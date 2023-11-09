package algogather.api.dto.sharedsourcecode;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EditedFeedbackResponseDto {
    private Long feedbackId;
    private String writerName;
    private String writerUserId;
    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long sourceCodeId;
    private Long lineNumber;

    @Builder

    public EditedFeedbackResponseDto(Long feedbackId, String writerName, String writerUserId, String comment, LocalDateTime createdAt, LocalDateTime updatedAt, Long sourceCodeId, Long lineNumber) {
        this.feedbackId = feedbackId;
        this.writerName = writerName;
        this.writerUserId = writerUserId;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sourceCodeId = sourceCodeId;
        this.lineNumber = lineNumber;
    }
}
