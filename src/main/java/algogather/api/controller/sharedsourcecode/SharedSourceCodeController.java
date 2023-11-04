package algogather.api.controller.sharedsourcecode;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shared-sourcecode")
public class SharedSourceCodeController {

    //TODO
//    @Operation(summary = "소스코드 공유 API", description = "소스코드 공유 API입니다.")
//    @ApiResponses(value = {
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//    })
//    @PostMapping
//    public ResponseEntity<ApiResponse</**/?>> saveSharedSourceCode(@Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
//
//    }

    //TODO
//    @Operation(summary = "스터디방 공유 소스코드 목록 조회 API", description = "스터디방 공유 소스코드 목록 조회 API입니다.")
//    @ApiResponses(value = {
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//    })
//    @GetMapping()
//    public ResponseEntity<ApiResponse</**/?>> saveSharedSourceCode(@Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
//
//    }

    //TODO
//    @Operation(summary = "특정 공유 소스코드 내용 조회 API", description = "특정 공유 소스코드 내용 조회 API입니다.")
//    @ApiResponses(value = {
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//    })
//    @GetMapping("/{sourcecodeId}")
//    public ResponseEntity<ApiResponse</**/?>> saveSharedSourceCode(Long sourcecodeId, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
//
//    }
}
