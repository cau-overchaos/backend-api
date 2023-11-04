package algogather.api.dto.sharedsourcecode;

import algogather.api.dto.problem.ProblemInfoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateSharedSourceCodeRequestForm {

    @NotEmpty
    private String title;

    @NotEmpty
    private String sourceCode;

    @NotNull
    private Long programmingLanguageId;

    private ProblemInfoRequestDto problemInfoRequestDto;
}
