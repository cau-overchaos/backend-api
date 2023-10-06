package algogather.api.dto.problem;

import algogather.api.domain.problem.ProblemProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ProblemInfoRequestDto {
    @Schema(description = "문제 출처")
    @NotNull(message = "문제 출처를 올바르게 입력해주세요")
    private ProblemProvider provider;

    @Schema(description = "문제 번호")
    @NotEmpty(message = "문제 번호를 입력해주세요.")
    private Long pid;

    @Builder
    public ProblemInfoRequestDto(ProblemProvider provider, Long pid) {
        this.provider = provider;
        this.pid = pid;
    }


}
