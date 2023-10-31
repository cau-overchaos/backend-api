package algogather.api.dto.studyroom;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class DeleteStudyRoomMemberRequestDto {

    @NotEmpty
    private String targetUserId;


    public DeleteStudyRoomMemberRequestDto(String targetUserId) {
        this.targetUserId = targetUserId;
    }
}
