package algogather.api.dto.studyroom;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class AddStudyRoomMemberRequestDto {
    @NotEmpty
    private String invitedUserId;

    public AddStudyRoomMemberRequestDto(String invitedUserId) {
        this.invitedUserId = invitedUserId;
    }
}
