package algogather.api.dto.sharedsourcecode;

import algogather.api.dto.problem.ProblemInfoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CreateSharedSourceCodeRequestForm {

    @NotEmpty
    @Size(max = 50, message = "제목은 50자 이하여야 합니다.")
    private String title;

    @NotEmpty
    @Size(max = 5000, message = "소스 코드의 길이는 5000자 이하여야 합니다.")
    private String sourceCode;

    @NotNull
    private Long programmingLanguageId;

    private ProblemInfoRequestDto problemInfoRequestDto;
}
