package algogather.api.dto.problem;

import algogather.api.domain.problem.ProblemProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class ProblemInfoDto {
    @Schema(description = "문제 출처")
    @NotEmpty(message = "문제 출처를 입력해주세요.")
    private ProblemProvider provider;

    @Schema(description = "문제 번호")
    @NotEmpty(message = "문제 번호를 입력해주세요.")
    private Long pid;

    public ProblemInfoDto(ProblemProvider provider, Long pid) {
        this.provider = provider;
        this.pid = pid;
    }
}
