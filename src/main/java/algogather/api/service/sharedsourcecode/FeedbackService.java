package algogather.api.service.sharedsourcecode;

import algogather.api.domain.sharedsourcecode.Feedback;
import algogather.api.domain.sharedsourcecode.FeedbackRepository;
import algogather.api.domain.sharedsourcecode.SharedSourceCode;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.sharedsourcecode.*;
import algogather.api.exception.sharedsourcecode.FeedbackNotFoundException;
import algogather.api.exception.sharedsourcecode.LineNumberExceedTotalLineCountException;
import algogather.api.exception.sharedsourcecode.NotSameLineNumberException;
import algogather.api.exception.sharedsourcecode.NotSameSourceCodeException;
import algogather.api.service.studyroom.StudyRoomService;
import algogather.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final StudyRoomService studyRoomService;
    private final SharedSourceCodeService sharedSourceCodeService;
    private final UserService userService;

    private final FeedbackRepository feedbackRepository;

    public CreatedFeedbackResponseDto saveFeedback(Long studyRoomId, Long sharedSourceCodeId, CreateFeedbackFormRequestDtoForm createFeedbackFormRequestDtoForm, UserAdapter userAdapter) {

        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디방 멤버만 피드백을 작성할 수 있다.
        SharedSourceCode sharedSourceCode = sharedSourceCodeService.findById(studyRoomId, sharedSourceCodeId, userAdapter);

        throwExceptionIfRequestLineNumberExceedTotalLineCount(createFeedbackFormRequestDtoForm.getLineNumber(), sharedSourceCode); // 소스코드의 줄 수를 확인하고 lineNumber가 코드의 줄 수보다 작거나 같은지 검증한다.

        /**
         * 요청 폼에서 parent id가 null이라면, 부모 피드백을 작성하여 저장하는 것이므로 replayParentFeedback에 null로 저장한다.
         * 요청 폼에서 parent id가 null이 아니라면, 대댓글을 작성하는 것이므로, replyParentFeedback에 해당 요청 parent로 저장한다.
         */
        Feedback replyParentFeedback = getReplyParentFeedback(createFeedbackFormRequestDtoForm, sharedSourceCode);

        Feedback newFeedback = Feedback.builder()
                .comment(createFeedbackFormRequestDtoForm.getComment())
                .sourceCodeLineNumber(createFeedbackFormRequestDtoForm.getLineNumber())
                .isDeleted(false)
                .sharedSourceCode(sharedSourceCode)
                .user(userAdapter.getUser())
                .replyParentFeedback(replyParentFeedback)
                .build();

        Feedback savedFeedback = feedbackRepository.save(newFeedback);

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

        SharedSourceCode sharedSourceCode = sharedSourceCodeService.findById(studyRoomId, sharedSourceCodeId, userAdapter);

        throwExceptionIfRequestLineNumberExceedTotalLineCount(lineNumber, sharedSourceCode); // 소스코드의 줄 수를 확인하고 lineNumber가 코드의 줄 수보다 작거나 같은지 검증한다.

        List<FeedbackGroupByParentResponseDto> feedbackGroupByParentResponseDtoList = getFeedbackGroupByParentResponseDtoList(sharedSourceCodeId, lineNumber);

        return FeedbackListByLineNumberResponseDto.builder()
                .sourceCodeId(sharedSourceCode.getId())
                .lineNumber(lineNumber)
                .feedbackGroupByParentResponseDtoList(feedbackGroupByParentResponseDtoList)
                .build();

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

    private Feedback getReplyParentFeedback(CreateFeedbackFormRequestDtoForm createFeedbackFormRequestDtoForm, SharedSourceCode sharedSourceCode) {
        Feedback replyParentFeedback = null;

        if(createFeedbackFormRequestDtoForm.getReplyParentFeedbackId() != null) {
            Feedback foundReplyParentFeedback = findById(createFeedbackFormRequestDtoForm.getReplyParentFeedbackId());

            if(foundReplyParentFeedback.getReplyParentFeedback() != null) { // 대댓글을 단 피드백이 루트 피드백이 아니라면
                foundReplyParentFeedback = findById(foundReplyParentFeedback.getReplyParentFeedback().getId()); // 루트 피드백을 가져온다.
            }

            if(!Objects.equals(foundReplyParentFeedback.getSharedSourceCode().getId(), sharedSourceCode.getId())) { // 부모 피드백이 같은 소스코드에 속하는지 검증한다.
                throw new NotSameSourceCodeException();
            }

            if(!Objects.equals(foundReplyParentFeedback.getSourceCodeLineNumber(), createFeedbackFormRequestDtoForm.getLineNumber())) { // 부모 피드백이 같은 소스코드 라인에 속하는지 검증한다.
                throw new NotSameLineNumberException();
            }

            replyParentFeedback = foundReplyParentFeedback;
        }
        return replyParentFeedback;
    }

    private Feedback findById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow(FeedbackNotFoundException::new);
    }


    private static void throwExceptionIfRequestLineNumberExceedTotalLineCount(Long requestLineNumber, SharedSourceCode sharedSourceCode) {
        String sourceCodeText = sharedSourceCode.getSourceCodeText();
        long totalLineCount = sourceCodeText.length() - sourceCodeText.replace(String.valueOf('\n'), "").length();
        totalLineCount++;

        if(requestLineNumber > totalLineCount) {
            throw new LineNumberExceedTotalLineCountException();
        }
    }
}
