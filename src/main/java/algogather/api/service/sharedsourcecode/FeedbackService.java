package algogather.api.service.sharedsourcecode;

import algogather.api.domain.sharedsourcecode.Feedback;
import algogather.api.domain.sharedsourcecode.FeedbackRepository;
import algogather.api.domain.sharedsourcecode.SharedSourceCode;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.sharedsourcecode.CreateFeedbackFormRequestDtoForm;
import algogather.api.dto.sharedsourcecode.FeedbackResponseDto;
import algogather.api.exception.sharedsourcecode.FeedbackNotFoundException;
import algogather.api.exception.sharedsourcecode.LineNumberExceedTotalLineCountException;
import algogather.api.exception.sharedsourcecode.NotSameLineNumberException;
import algogather.api.exception.sharedsourcecode.NotSameSourceCodeException;
import algogather.api.service.studyroom.StudyRoomService;
import algogather.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final StudyRoomService studyRoomService;
    private final SharedSourceCodeService sharedSourceCodeService;
    private final UserService userService;

    private final FeedbackRepository feedbackRepository;

    public FeedbackResponseDto saveFeedback(Long studyRoomId, Long sharedSourceCodeId, CreateFeedbackFormRequestDtoForm createFeedbackFormRequestDtoForm, UserAdapter userAdapter) {

        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디방 멤버만 피드백을 작성할 수 있다.
        SharedSourceCode sharedSourceCode = sharedSourceCodeService.findById(studyRoomId, sharedSourceCodeId, userAdapter);

        throwExceptionIfRequestLineNumberExceedTotalLineCount(createFeedbackFormRequestDtoForm, sharedSourceCode); // 소스코드의 줄 수를 확인하고 lineNumber가 코드의 줄 수보다 작거나 같은지 검증한다.

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

        return FeedbackResponseDto.builder()
                .id(savedFeedback.getId())
                .comment(savedFeedback.getComment())
                .writerUserId(savedFeedback.getUser().getUserId())
                .writerName(savedFeedback.getUser().getName())
                .isDeleted(savedFeedback.getIsDeleted())
                .createdAt(savedFeedback.getCreatedAt())
                .updatedAt(savedFeedback.getUpdatedAt())
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


    private static void throwExceptionIfRequestLineNumberExceedTotalLineCount(CreateFeedbackFormRequestDtoForm createFeedbackFormRequestDtoForm, SharedSourceCode sharedSourceCode) {
        String sourceCodeText = sharedSourceCode.getSourceCodeText();
        long totalLineCount = sourceCodeText.length() - sourceCodeText.replace(String.valueOf('\n'), "").length();
        totalLineCount++;

        if(createFeedbackFormRequestDtoForm.getLineNumber() > totalLineCount) {
            throw new LineNumberExceedTotalLineCountException();
        }
    }
}
