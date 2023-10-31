package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoomRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudyRoomMemberInfoDto {
    private String userId;
    private String name;
    private Boolean isValidJudgeAccount;
    private String judgeAccount;
    private StudyRoomRole studyRoomRole;
    private String studyRoomRoleName;
    private Long tierLevel;
    private Boolean isMe; // 현재 인원 페이지를 보고 있는, 로그인한 사용자인지 여부를 나타낸다

    @Builder
    public StudyRoomMemberInfoDto(String userId, String name, Boolean isValidJudgeAccount, String judgeAccount, StudyRoomRole studyRoomRole, String studyRoomRoleName, Long tierLevel, boolean isMe) {
        this.userId = userId;
        this.name = name;
        this.isValidJudgeAccount = isValidJudgeAccount;
        this.judgeAccount = judgeAccount;
        this.studyRoomRole = studyRoomRole;
        this.studyRoomRoleName = studyRoomRoleName;
        this.tierLevel = tierLevel;
        this.isMe = isMe;
    }
}
