package algogather.api.controller.studyroom;

import algogather.api.domain.problem.ProblemProvider;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.problem.ProblemResponseDto;
import algogather.api.service.studyroom.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    @Operation(summary = "문제 조회", description = "문제 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<ProblemResponseDto>> findProblemByPidAndProvider(@RequestParam Long pid, @RequestParam ProblemProvider provider) {
        ProblemResponseDto problemResponseDto = problemService.findProblemByPidAndProviderAndReturnDto(pid, provider);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(problemResponseDto, "해당 문제를 찾았습니다."));
    }
}
