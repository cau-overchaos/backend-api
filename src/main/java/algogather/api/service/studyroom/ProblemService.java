package algogather.api.service.studyroom;

import algogather.api.domain.problem.Problem;
import algogather.api.domain.problem.ProblemProvider;
import algogather.api.domain.problem.ProblemRepository;
import algogather.api.dto.problem.ProblemResponseDto;
import algogather.api.exception.ProblemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    public ProblemResponseDto findProblemByPidAndProvider(Long pid, ProblemProvider provider) {
        Problem foundProblem = problemRepository.findByPidAndProvider(pid, provider).orElseThrow(() -> new ProblemNotFoundException());

        return new ProblemResponseDto(foundProblem);
    }
}
