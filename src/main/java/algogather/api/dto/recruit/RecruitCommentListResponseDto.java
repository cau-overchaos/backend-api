package algogather.api.dto.recruit;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RecruitCommentListResponseDto {
    private boolean isCurrentUserStudyRoomManager; // 현재 로그인한 사용자가 스터디방 매니저인지 여부, 스터디방 매니저일때만 초대 버튼이 떠야한다.

    private List<RecruitCommentResponseDto> recruitCommentResponseDtoList;

    @Builder
    public RecruitCommentListResponseDto(boolean isCurrentUserStudyRoomManager, List<RecruitCommentResponseDto> recruitCommentResponseDtoList) {
        this.isCurrentUserStudyRoomManager = isCurrentUserStudyRoomManager;
        this.recruitCommentResponseDtoList = recruitCommentResponseDtoList;
    }
}
