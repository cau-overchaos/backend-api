package algogather.api.dto.sharedsourcecode;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedbackResponseDto {
    private Long feedbackId;
    private String writerName;
    private String writerUserId;

    private String comment;

    private Boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public FeedbackResponseDto(Long feedbackId, String writerName, String writerUserId, String comment, Boolean isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.feedbackId = feedbackId;
        this.writerName = writerName;
        this.writerUserId = writerUserId;
        this.comment = comment;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
