package algogather.api.dto.sharedsourcecode;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class EditFeedbackRequestForm {

    @NotEmpty
    private String comment;
}
