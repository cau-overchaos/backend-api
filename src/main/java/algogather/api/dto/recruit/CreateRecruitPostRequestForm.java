package algogather.api.dto.recruit;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateRecruitPostRequestForm {
    @NotNull
    private Long studyRoomId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;
}
