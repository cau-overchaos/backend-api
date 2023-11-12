package algogather.api.dto.recruit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreatedRecruitCommentResponseDto {
    private Long id;
    private String comment;
    private String writerUserId;
    private String writerUserName;
    private Long recruitPostId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public CreatedRecruitCommentResponseDto(Long id, String comment, String writerUserId, String writerUserName, Long recruitPostId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.comment = comment;
        this.writerUserId = writerUserId;
        this.writerUserName = writerUserName;
        this.recruitPostId = recruitPostId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
