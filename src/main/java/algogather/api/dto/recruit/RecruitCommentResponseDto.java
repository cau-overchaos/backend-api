package algogather.api.dto.recruit;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecruitCommentResponseDto {
    private Long id;
    private String comment;
    private String writerUserId;
    private String writerUserName;
    private Long recruitPostId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isAlreadyStudyMember; // 댓글 단 사람이 이미 스터디 방 회원인지 여부, 회원이면 초대 버튼 없어야함

    @Builder

    public RecruitCommentResponseDto(Long id, String comment, String writerUserId, String writerUserName, Long recruitPostId, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isAlreadyStudyMember) {
        this.id = id;
        this.comment = comment;
        this.writerUserId = writerUserId;
        this.writerUserName = writerUserName;
        this.recruitPostId = recruitPostId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isAlreadyStudyMember = isAlreadyStudyMember;
    }
}
