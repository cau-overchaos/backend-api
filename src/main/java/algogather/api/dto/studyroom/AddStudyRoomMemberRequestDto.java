package algogather.api.dto.studyroom;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddStudyRoomMemberRequestDto {
    private String invitedUserId;

    public AddStudyRoomMemberRequestDto(String invitedUserId) {
        this.invitedUserId = invitedUserId;
    }
}
