package algogather.api.dto.problem;

import algogather.api.domain.problem.Problem;
import algogather.api.domain.problem.ProblemProvider;
import lombok.Getter;

@Getter
public class ProblemResponseDto {
    private Long id;

    private ProblemProvider provider;

    private Long pid;

    private String title;

    private Long difficultyLevel;

    private String difficultyName;

    public ProblemResponseDto(Problem problem) {
        this.id = problem.getId();
        this.provider = problem.getProvider();
        this.pid = problem.getPid();
        this.title = problem.getTitle();
        this.difficultyLevel = problem.getDifficulty().getLevel();
        this.difficultyName = problem.getDifficulty().getName();
    }
}
