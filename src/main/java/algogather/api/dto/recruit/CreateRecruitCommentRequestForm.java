package algogather.api.dto.recruit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CreateRecruitCommentRequestForm {

    @NotEmpty
    @Size(max = 150, message = "내용은 150자 이하여야 합니다.")
    private String comment;

    @Builder
    public CreateRecruitCommentRequestForm(String comment) {
        this.comment = comment;
    }
}
