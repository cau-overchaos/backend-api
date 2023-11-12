package algogather.api.dto.compile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Getter
@NoArgsConstructor
public class CompilerRequestDto {
    private String language;

    private String code;

    private List<String> input;

    @Builder
    public CompilerRequestDto(String language, String code, List<String> input) {
        this.language = language;
        this.code = code;
        this.input = input;
    }
}
