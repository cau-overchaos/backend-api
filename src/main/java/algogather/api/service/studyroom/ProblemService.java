package algogather.api.service.studyroom;

import algogather.api.domain.problem.Problem;
import algogather.api.domain.problem.ProblemProvider;
import algogather.api.domain.problem.ProblemRepository;
import algogather.api.dto.problem.ProblemInfoDto;
import algogather.api.dto.problem.ProblemResponseDto;
import algogather.api.exception.ProblemNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    public ProblemResponseDto findProblemByPidAndProviderAndReturnDto(Long pid, ProblemProvider provider) {
        Problem foundProblem = problemRepository.findByPidAndProvider(pid, provider).orElseThrow(() -> new ProblemNotFoundException());

        return new ProblemResponseDto(foundProblem);
    }

    public Problem findByPidAndProvider(Long pid, ProblemProvider provider) {
        Problem foundProblem = problemRepository.findByPidAndProvider(pid, provider).orElseThrow(() -> new ProblemNotFoundException());

        return foundProblem;
    }

    public void validateProblems(List<ProblemInfoDto> problemInfoDtoList) {
        problemInfoDtoList.stream()
                .map(problemInfo -> findProblemByPidAndProviderAndReturnDto(problemInfo.getPid(), problemInfo.getProvider()));
    }
}
