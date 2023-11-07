package algogather.api.dto.sharedsourcecode;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedbackResponseDto {
    private Long id;
    private String writerName;
    private String writerUserId;

    private Boolean isDeleted;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public FeedbackResponseDto(Long id, String writerName, String writerUserId, Boolean isDeleted, String comment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.writerName = writerName;
        this.writerUserId = writerUserId;
        this.isDeleted = isDeleted;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
