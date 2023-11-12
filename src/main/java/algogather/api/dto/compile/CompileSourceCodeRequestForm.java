package algogather.api.dto.compile;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CompileSourceCodeRequestForm {
    @NotNull
    private Long programmingLanguageId;

    @NotEmpty
    private String sourceCodeText;

    @NotNull
    private String input;

    public CompileSourceCodeRequestForm(Long programmingLanguageId, String sourceCodeText, String input) {
        this.programmingLanguageId = programmingLanguageId;
        this.sourceCodeText = sourceCodeText;
        this.input = input;
    }
}
