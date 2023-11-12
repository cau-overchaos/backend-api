package algogather.api.controller.recruit;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.recruit.CreateRecruitPostRequestForm;
import algogather.api.dto.recruit.CreatedRecruitPostResponseDto;
import algogather.api.service.recruit.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruits")
@Tag(name = "스터디원 모집", description = "스터디원 모집 관련 API 입니다.")
public class RecruitController {

    private final RecruitService recruitService;
    @Operation(summary = "스터디방 모집글 작성", description = "문제 작성 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponse<CreatedRecruitPostResponseDto>> saveRecruitPost(@Valid @RequestBody CreateRecruitPostRequestForm createRecruitPostRequestForm, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        CreatedRecruitPostResponseDto createdRecruitPostResponseDto = recruitService.saveRecruitPost(createRecruitPostRequestForm, userAdapter);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(createdRecruitPostResponseDto, "스터디방 모집글을 성공적으로 작성하였습니다."));

    }
}
