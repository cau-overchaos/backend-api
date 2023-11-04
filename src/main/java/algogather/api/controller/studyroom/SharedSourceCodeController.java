package algogather.api.controller.studyroom;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.sharedsourcecode.CreateSharedSourceCodeRequestForm;
import algogather.api.dto.sharedsourcecode.SharedSourceCodeResponseDto;
import algogather.api.service.sharedsourcecode.SharedSourceCodeService;
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
@RequestMapping("/studyrooms")
@Tag(name = "공유 소스코드", description = "공유 소스코드 관련 API 입니다.")
public class SharedSourceCodeController {

    private final SharedSourceCodeService sharedSourceCodeService;
    //TODO
    @Operation(summary = "공유 소스코드 작성 API", description = "공유 소스코드 작성 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))


    })
    @PostMapping("/{studyRoomId}/shared-sourcecodes")
    public ResponseEntity<ApiResponse<SharedSourceCodeResponseDto>> saveSharedSourceCode(@PathVariable Long studyRoomId, @Valid @RequestBody CreateSharedSourceCodeRequestForm createSharedSourceCodeRequestForm, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        SharedSourceCodeResponseDto sharedSourceCodeResponseDto = sharedSourceCodeService.saveSharedSourceCode(studyRoomId, createSharedSourceCodeRequestForm, userAdapter);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(sharedSourceCodeResponseDto, "공유 소스코드를 성공적으로 저장하였습니다."));
    }

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
