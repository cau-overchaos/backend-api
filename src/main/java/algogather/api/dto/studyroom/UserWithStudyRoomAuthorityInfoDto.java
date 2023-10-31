package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoomRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserWithStudyRoomAuthorityInfoDto {
    private Long id;
    private String userId;
    private String name;
    private String judgeAccount;
    private StudyRoomRole studyRoomRole;

    public UserWithStudyRoomAuthorityInfoDto(Long id, String userId, String name, String judgeAccount, StudyRoomRole studyRoomRole) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.judgeAccount = judgeAccount;
        this.studyRoomRole = studyRoomRole;
    }
}
