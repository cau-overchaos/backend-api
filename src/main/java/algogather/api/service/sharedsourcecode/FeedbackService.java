package algogather.api.service.sharedsourcecode;

import algogather.api.domain.sharedsourcecode.Feedback;
import algogather.api.domain.sharedsourcecode.FeedbackRepository;
import algogather.api.domain.sharedsourcecode.SharedSourceCode;
import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.sharedsourcecode.*;
import algogather.api.exception.sharedsourcecode.*;
import algogather.api.service.notification.NotificationService;
import algogather.api.service.studyroom.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final StudyRoomService studyRoomService;
    private final SharedSourceCodeService sharedSourceCodeService;
    private final NotificationService notificationService;

    private final FeedbackRepository feedbackRepository;

    @Transactional
    public CreatedFeedbackResponseDto saveFeedback(Long studyRoomId, Long sharedSourceCodeId, CreateFeedbackRequestForm createFeedbackRequestForm, UserAdapter userAdapter) {

        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디방 멤버만 피드백을 작성할 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(studyRoomId);

        SharedSourceCode sharedSourceCode = sharedSourceCodeService.findById(studyRoom.getId(), sharedSourceCodeId, userAdapter);

        throwExceptionIfRequestLineNumberExceedTotalLineCount(createFeedbackRequestForm.getLineNumber(), sharedSourceCode); // 소스코드의 줄 수를 확인하고 lineNumber가 코드의 줄 수보다 작거나 같은지 검증한다.

        /**
         * 요청 폼에서 parent id가 null이라면, 부모 피드백을 작성하여 저장하는 것이므로 replayParentFeedback에 null로 저장한다.
         * 요청 폼에서 parent id가 null이 아니라면, 대댓글을 작성하는 것이므로, replyParentFeedback에 해당 요청 parent로 저장한다.
         */
        Feedback replyParentFeedback = getReplyParentFeedback(createFeedbackRequestForm, sharedSourceCode);

        Feedback newFeedback = Feedback.builder()
                .comment(createFeedbackRequestForm.getComment())
                .sourceCodeLineNumber(createFeedbackRequestForm.getLineNumber())
                .isDeleted(false)
                .sharedSourceCode(sharedSourceCode)
                .user(userAdapter.getUser())
                .replyParentFeedback(replyParentFeedback)
                .build();

        Feedback savedFeedback = feedbackRepository.save(newFeedback);

        if(!Objects.equals(newFeedback.getUser().getId(), sharedSourceCode.getUser().getId())) { // 다른 사람이 피드백을 달았을 때만 알림이 온다.
            String content =  "\"" + newFeedback.getUser().getName() + "\" 님이 \"" + sharedSourceCode.getTitle() + "\" 풀이에 피드백을 달았습니다.";
            notificationService.addNotification(content, sharedSourceCode.getUser());
        }

        return CreatedFeedbackResponseDto.builder()
                .feedbackId(savedFeedback.getId())
                .comment(savedFeedback.getComment())
                .writerUserId(savedFeedback.getUser().getUserId())
                .writerName(savedFeedback.getUser().getName())
                .createdAt(savedFeedback.getCreatedAt())
                .updatedAt(savedFeedback.getUpdatedAt())
                .sourceCodeId(savedFeedback.getSharedSourceCode().getId())
                .lineNumber(savedFeedback.getSourceCodeLineNumber())
                .build();
    }

    //TODO 예외 경우가 있을 수 있으니 테스트 코드 작성 권장
    public FeedbackListByLineNumberResponseDto findFeedbackListByLineNumber(Long studyRoomId, Long sharedSourceCodeId, Long lineNumber, UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디방 멤버만 피드백을 조회할 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(studyRoomId);

        SharedSourceCode sharedSourceCode = sharedSourceCodeService.findById(studyRoom.getId(), sharedSourceCodeId, userAdapter);

        throwExceptionIfRequestLineNumberExceedTotalLineCount(lineNumber, sharedSourceCode); // 소스코드의 줄 수를 확인하고 lineNumber가 코드의 줄 수보다 작거나 같은지 검증한다.

        List<FeedbackGroupByParentResponseDto> feedbackGroupByParentResponseDtoList = getFeedbackGroupByParentResponseDtoList(sharedSourceCodeId, lineNumber);

        return FeedbackListByLineNumberResponseDto.builder()
                .sourceCodeId(sharedSourceCode.getId())
                .lineNumber(lineNumber)
                .feedbackGroupByParentResponseDtoList(feedbackGroupByParentResponseDtoList)
                .build();

    }

    @Transactional
    public EditedFeedbackResponseDto edit(Long studyRoomId, Long sharedSourceCodeId, Long feedbackId, EditFeedbackRequestForm editFeedbackRequestForm, UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 특정 공유 소스코드를 수정할 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(studyRoomId);

        SharedSourceCode sharedSourceCode = sharedSourceCodeService.findById(studyRoom.getId(), sharedSourceCodeId, userAdapter);

        Feedback targetFeedback = feedbackRepository.findById(feedbackId).orElseThrow(FeedbackNotFoundException::new);

        validateFeedbackAndSharedSourceCodeMatching(targetFeedback, sharedSourceCode); // 그 피드백이 해당 소스코드에 속하는지 검증

        validateFeedbackWriter(userAdapter, targetFeedback); // 현재 로그인한 유저가 피드백을 쓴 사람인지 검증

        targetFeedback.changeComment(editFeedbackRequestForm.getComment());

        return EditedFeedbackResponseDto.builder()
                .feedbackId(targetFeedback.getId())
                .comment(targetFeedback.getComment())
                .writerUserId(targetFeedback.getUser().getUserId())
                .writerName(targetFeedback.getUser().getName())
                .createdAt(targetFeedback.getCreatedAt())
                .updatedAt(targetFeedback.getUpdatedAt())
                .sourceCodeId(targetFeedback.getSharedSourceCode().getId())
                .lineNumber(targetFeedback.getSourceCodeLineNumber())
                .build();
    }

    @Transactional
    public void delete(Long studyRoomId, Long sharedSourceCodeId, Long feedbackId, UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 특정 공유 소스코드를 삭제할 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(studyRoomId);

        SharedSourceCode sharedSourceCode = sharedSourceCodeService.findById(studyRoom.getId(), sharedSourceCodeId, userAdapter);

        Feedback targetFeedback = feedbackRepository.findById(feedbackId).orElseThrow(FeedbackNotFoundException::new);

        validateFeedbackAndSharedSourceCodeMatching(targetFeedback, sharedSourceCode); // 그 피드백이 해당 소스코드에 속하는지 검증

        validateFeedbackWriter(userAdapter, targetFeedback); // 현재 로그인한 유저가 피드백을 쓴 사람인지 검증

        if(targetFeedback.getIsDeleted()) {
            throw  new AlreadyDeletedFeedback();
        }
        else {
            targetFeedback.changeIsDeletedToTrue(); // 삭제된 메시지는 DB에서 실제로 삭제되지는 않고 isDeleted만 바꾸어 유저애게 '삭제된 메시지입니다.'라고 보이도록 한다.
        }
    }

    public CountFeedbackByLineListResponseDto countFeedbackOfSourceCodeLines(Long studyRoomId, Long sharedSourceCodeId, UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 특정 공유 소스코드의 특정 라인의 피드백 개수를 확인할 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(studyRoomId);

        SharedSourceCode sharedSourceCode = sharedSourceCodeService.findById(studyRoom.getId(), sharedSourceCodeId, userAdapter);

//        throwExceptionIfRequestLineNumberExceedTotalLineCount(lineNumber, sharedSourceCode); // 소스코드의 줄 수를 확인하고 lineNumber가 코드의 줄 수보다 작거나 같은지 검증한다.

        long sourceCodeTotalLineCount = getSourceCodeTotalLineCount(sharedSourceCode);

        List<CountFeedbackByLineResponseDto> countFeedbackByLineResponseDtoList = new ArrayList<>();

        for(long line = 1; line <= sourceCodeTotalLineCount; line++) {
            Long feedbackCountByLine = feedbackRepository.countBySharedSourceCodeIdAndSourceCodeLineNumberAndIsDeletedIsFalse(sharedSourceCode.getId(), line);

            countFeedbackByLineResponseDtoList.add(CountFeedbackByLineResponseDto.builder()
                    .lineNumber(line)
                    .feedbackCount(feedbackCountByLine)
                    .build());
        }

        return CountFeedbackByLineListResponseDto.builder()
                .sharedSourceCodeId(sharedSourceCode.getId())
                .countFeedbackByLineResponseDtoList(countFeedbackByLineResponseDtoList)
                .build();

    }

    private static void validateFeedbackAndSharedSourceCodeMatching(Feedback feedback, SharedSourceCode sharedSourceCode) {
        if(!Objects.equals(feedback.getSharedSourceCode().getId(), sharedSourceCode.getId())) {
            throw new SharedSourceCodeAndFeedbackNotMatchingException();
        }
    }

    private static void validateFeedbackWriter(UserAdapter userAdapter, Feedback feedback) {
        if(!Objects.equals(userAdapter.getUser().getId(), feedback.getUser().getId())) {
            throw new NotFeedbackWriterException();
        }
    }

    private List<FeedbackGroupByParentResponseDto> getFeedbackGroupByParentResponseDtoList(Long sharedSourceCodeId, Long lineNumber) {
        List<FeedbackGroupByParentResponseDto> feedbackGroupByParentResponseDtoList = new ArrayList<>();
        List<Feedback> parentFeedbackListByLineNumber = feedbackRepository.findBySharedSourceCodeIdAndSourceCodeLineNumberAndReplyParentFeedbackIsNull(sharedSourceCodeId, lineNumber);

        for (Feedback parentFeedback : parentFeedbackListByLineNumber) { // 부모 피드백별로 자식 피드백을 찾는다.
            FeedbackResponseDto parentFeedbackResponseDto = getFeedbackResponseDto(parentFeedback);

            List<FeedbackResponseDto> childrenFeedbackResponseDto = new ArrayList<>();
            List<Feedback> childrenFeedbackList = feedbackRepository.findByReplyParentFeedbackId(parentFeedback.getId());

            for (Feedback childrenFeedback : childrenFeedbackList) {
                childrenFeedbackResponseDto.add(getFeedbackResponseDto(childrenFeedback));
            }

            feedbackGroupByParentResponseDtoList.add(FeedbackGroupByParentResponseDto.builder()
                    .parentId(parentFeedback.getId())
                    .parentFeedbackResponseDto(parentFeedbackResponseDto)
                    .childrenFeedbackResponseDtoList(childrenFeedbackResponseDto)
                    .build());
        }
        return feedbackGroupByParentResponseDtoList;
    }

    /**
     * 피드백이 삭제된 경우까지 고려하여 피드백 DTO를 만든다.
     */
    private static FeedbackResponseDto getFeedbackResponseDto(Feedback feedback) {
        return FeedbackResponseDto
                .builder()
                .feedbackId(feedback.getId())
                .writerUserId(feedback.getIsDeleted() ? null : feedback.getUser().getUserId())
                .writerName(feedback.getIsDeleted() ? null : feedback.getUser().getName())
                .comment(feedback.getIsDeleted() ? "삭제된 피드백입니다." : feedback.getComment())
                .isDeleted(feedback.getIsDeleted())
                .createdAt(feedback.getCreatedAt())
                .updatedAt(feedback.getUpdatedAt())
                .build();
    }

    private Feedback getReplyParentFeedback(CreateFeedbackRequestForm createFeedbackRequestForm, SharedSourceCode sharedSourceCode) {
        Feedback replyParentFeedback = null;

        if(createFeedbackRequestForm.getReplyParentFeedbackId() != null) {
            Feedback foundReplyParentFeedback = findById(createFeedbackRequestForm.getReplyParentFeedbackId());

            if(foundReplyParentFeedback.getReplyParentFeedback() != null) { // 대댓글을 단 피드백이 루트 피드백이 아니라면
                foundReplyParentFeedback = findById(foundReplyParentFeedback.getReplyParentFeedback().getId()); // 루트 피드백을 가져온다.
            }

            if(!Objects.equals(foundReplyParentFeedback.getSharedSourceCode().getId(), sharedSourceCode.getId())) { // 부모 피드백이 같은 소스코드에 속하는지 검증한다.
                throw new NotSameSourceCodeException();
            }

            if(!Objects.equals(foundReplyParentFeedback.getSourceCodeLineNumber(), createFeedbackRequestForm.getLineNumber())) { // 부모 피드백이 같은 소스코드 라인에 속하는지 검증한다.
                throw new NotSameLineNumberException();
            }

            replyParentFeedback = foundReplyParentFeedback;
        }
        return replyParentFeedback;
    }

    private Feedback findById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow(FeedbackNotFoundException::new);
    }


    private void throwExceptionIfRequestLineNumberExceedTotalLineCount(Long requestLineNumber, SharedSourceCode sharedSourceCode) {
        long totalLineCount = getSourceCodeTotalLineCount(sharedSourceCode);

        if(requestLineNumber > totalLineCount) {
            throw new LineNumberExceedTotalLineCountException();
        }
    }

    private long getSourceCodeTotalLineCount(SharedSourceCode sharedSourceCode) {
        String sourceCodeText = sharedSourceCode.getSourceCodeText();
        long totalLineCount = sourceCodeText.length() - sourceCodeText.replace(String.valueOf('\n'), "").length();
        totalLineCount++;

        return totalLineCount;
    }


}
