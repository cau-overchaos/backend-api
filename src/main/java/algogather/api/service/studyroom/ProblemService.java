package algogather.api.service.studyroom;

import algogather.api.domain.problem.Problem;
import algogather.api.domain.problem.ProblemProvider;
import algogather.api.domain.problem.ProblemRepository;
import algogather.api.dto.problem.ProblemInfoRequestDto;
import algogather.api.dto.problem.ProblemResponseDto;
import algogather.api.exception.problem.ProblemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    public ProblemResponseDto findProblemByPidAndProviderAndReturnDto(Long pid, ProblemProvider provider) {
        Problem foundProblem = problemRepository.findByPidAndProvider(pid, provider)
                .orElseThrow(
                        () -> new ProblemNotFoundException(provider.getValue() + "의 " + pid + "번 문제를 찾을 수 없습니다.")
                );

        return new ProblemResponseDto(foundProblem);
    }

    public Problem findByPidAndProvider(Long pid, ProblemProvider provider) {
        Problem foundProblem = problemRepository.findByPidAndProvider(pid, provider)
                .orElseThrow(
                        () -> new ProblemNotFoundException(provider.getValue() + "의 " + pid + "번 문제를 찾을 수 없습니다.")
                );

        return foundProblem;
    }

    public void validateProblems(List<ProblemInfoRequestDto> problemInfoRequestDtoList) {
        problemInfoRequestDtoList.stream()
                .map(problemInfo -> findProblemByPidAndProviderAndReturnDto(problemInfo.getPid(), problemInfo.getProvider()));
    }
}
