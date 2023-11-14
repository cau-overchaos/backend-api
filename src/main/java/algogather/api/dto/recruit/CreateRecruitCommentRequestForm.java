package algogather.api.dto.recruit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CreateRecruitCommentRequestForm {

    @NotEmpty
    private String comment;

    @Builder
    public CreateRecruitCommentRequestForm(String comment) {
        this.comment = comment;
    }
}
