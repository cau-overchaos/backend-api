package algogather.api.dto.compile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CompilerRequestDto {

    private String language;

    private String code;

    private String input;

    @Builder
    public CompilerRequestDto(String language, String code, String input) {
        this.language = language;
        this.code = code;
        this.input = input;
    }
}
