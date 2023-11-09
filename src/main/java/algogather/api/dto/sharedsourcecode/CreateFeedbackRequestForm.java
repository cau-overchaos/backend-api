package algogather.api.dto.sharedsourcecode;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateFeedbackRequestForm {

    @NotNull
    @Min(value = 1, message = "코드 줄 번호는 1부터 시작해야 합니다.")
    private Long lineNumber;

    @NotEmpty
    private String comment;

    private Long replyParentFeedbackId;
}
