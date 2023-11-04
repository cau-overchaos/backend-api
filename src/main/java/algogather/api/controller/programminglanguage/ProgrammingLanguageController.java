package algogather.api.controller.programminglanguage;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.programminglanguage.ProgrammingLanguageListResponseDto;
import algogather.api.service.programmingllanguage.ProgrammingLanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/programming-language")
public class ProgrammingLanguageController {

    private final ProgrammingLanguageService programmingLanguageService;

    @Operation(summary = "프로그래밍 언어 목록 조회 API", description = "프로그래밍 언어 목록 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<ProgrammingLanguageListResponseDto>> findAll(@Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        ProgrammingLanguageListResponseDto programmingLanguageListResponseDto = programmingLanguageService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(programmingLanguageListResponseDto, "모든 프로그래밍 언어 목록을 조회했습니다."));
    }
}
