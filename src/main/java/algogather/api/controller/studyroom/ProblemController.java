package algogather.api.controller.studyroom;

import algogather.api.domain.problem.ProblemProvider;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.problem.ProblemResponseDto;
import algogather.api.service.studyroom.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> findProblemByPidAndProvider(@RequestParam Long pid, @RequestParam ProblemProvider provider) {
        ProblemResponseDto problemResponseDto = problemService.findProblemByPidAndProvider(pid, provider);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(problemResponseDto, "해당 문제를 찾았습니다."));
    }
}
