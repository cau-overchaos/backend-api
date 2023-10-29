package algogather.api.dto.studyroom;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class ChangeStudyRoomAuthorityRequestDto {
    @NotEmpty
    private String targetUserId;

    public ChangeStudyRoomAuthorityRequestDto(String targetUserId) {
        this.targetUserId = targetUserId;
    }
}
