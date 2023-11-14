package algogather.api.dto.sharedsourcecode;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
public class CreateFeedbackRequestForm {

    @NotNull
    @Min(value = 1, message = "코드 줄 번호는 1부터 시작해야 합니다.")
    private Long lineNumber;

    @NotEmpty
    @Size(max = 150, message = "피드백 길이는 150자 이하여야 합니다.")
    private String comment;

    private Long replyParentFeedbackId;
}
