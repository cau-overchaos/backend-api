package algogather.api.controller.recruit;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.recruit.CreateRecruitPostRequestForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruits")
@Tag(name = "스터디원 모집", description = "스터디원 모집 관련 API 입니다.")
public class RecruitController {

//    @Operation(summary = "스터디방 모집글 작성", description = "문제 작성 API입니다.")
//    @ApiResponses(value = {
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//    })
//    @GetMapping
//    public Object saveRecruitPost(@Valid @RequestParam CreateRecruitPostRequestForm createRecruitPostRequestForm, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
//
//    }
}
