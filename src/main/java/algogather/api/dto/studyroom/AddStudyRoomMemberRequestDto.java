package algogather.api.dto.studyroom;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class AddStudyRoomMemberRequestDto {

    @NotEmpty
    private String targetUserId;

    public AddStudyRoomMemberRequestDto(String invitedUserId) {
        this.targetUserId = invitedUserId;
    }
}
