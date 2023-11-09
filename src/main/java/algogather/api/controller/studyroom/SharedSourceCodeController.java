package algogather.api.controller.studyroom;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.sharedsourcecode.*;
import algogather.api.service.sharedsourcecode.FeedbackService;
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
    private final FeedbackService feedbackService;
    @Operation(summary = "공유 소스코드 작성 API", description = "공유 소스코드 작성 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))


    })
    @PostMapping("/{studyRoomId}/shared-sourcecodes")
    public ResponseEntity<ApiResponse<SharedSourceCodeResponseDto>> saveSharedSourceCode(@PathVariable Long studyRoomId, @Valid @RequestBody CreateSharedSourceCodeRequestForm createSharedSourceCodeRequestForm, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        SharedSourceCodeResponseDto sharedSourceCodeResponseDto = sharedSourceCodeService.save(studyRoomId, createSharedSourceCodeRequestForm, userAdapter);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(sharedSourceCodeResponseDto, "공유 소스코드를 성공적으로 저장하였습니다."));
    }

    @Operation(summary = "스터디방 공유 소스코드 목록 조회 API", description = "스터디방 공유 소스코드 목록 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{studyRoomId}/shared-sourcecodes")
    public ResponseEntity<ApiResponse<SharedSourceCodeListResponseDto>> findAllSharedSourceCodeByStudyRoomId(@PathVariable Long studyRoomId, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        SharedSourceCodeListResponseDto sharedSourceCodeListResponseDto = sharedSourceCodeService.findAllByStudyRoomId(studyRoomId, userAdapter);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(sharedSourceCodeListResponseDto, "공유 소스코드 목록을 성공적으로 조회하였습니다."));
    }

    @Operation(summary = "특정 공유 소스코드 내용 조회 API", description = "특정 공유 소스코드 내용 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{studyRoomId}/shared-sourcecodes/{sourceCodeId}")
    public ResponseEntity<ApiResponse<SharedSourceCodeResponseDto>> findSharedSourceCodeById(@PathVariable Long studyRoomId, @PathVariable Long sourceCodeId, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        SharedSourceCodeResponseDto sharedSourceCodeResponseDto = sharedSourceCodeService.findByIdAndReturnResponseDto(studyRoomId, sourceCodeId, userAdapter);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(sharedSourceCodeResponseDto, "공유 소스코드를 성공적으로 조회하였습니다."));
    }

    @Operation(summary = "피드백 작성 API", description = "피드백 작성 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))


    })
    @PostMapping("/{studyRoomId}/shared-sourcecodes/{sharedSourceCodeId}/feedbacks")
    public ResponseEntity<ApiResponse<CreatedFeedbackResponseDto>> saveFeedback(@PathVariable Long studyRoomId, @PathVariable Long sharedSourceCodeId, @Valid @RequestBody CreateFeedbackRequestForm createFeedbackRequestForm, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        CreatedFeedbackResponseDto createdFeedbackResponseDto = feedbackService.saveFeedback(studyRoomId, sharedSourceCodeId, createFeedbackRequestForm, userAdapter);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(createdFeedbackResponseDto, "피드백을 성공적으로 저장하였습니다."));
    }

    @Operation(summary = "특정 코드 라인 피드백 목록 조회 API", description = "특정 코드 라인 피드백 목록 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))


    })
    @GetMapping("/{studyRoomId}/shared-sourcecodes/{sharedSourceCodeId}/feedbacks")
    public ResponseEntity<ApiResponse<FeedbackListByLineNumberResponseDto>> findFeedbackListByLineNumber(@PathVariable Long studyRoomId, @PathVariable Long sharedSourceCodeId, @RequestParam Long lineNumber,  @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        FeedbackListByLineNumberResponseDto feedbackListByLineNumberResponseDto = feedbackService.findFeedbackListByLineNumber(studyRoomId, sharedSourceCodeId, lineNumber, userAdapter);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(feedbackListByLineNumberResponseDto, "피드백을 성공적으로 조회하였습니다."));
    }

    //TODO 라인별로 피드백 개수 몇개 달려있는지 API 만들기
//    @Operation(summary = "라인별 피드백 개수 API", description = "라인별 피드백 개수 API입니다.")
//    @ApiResponses(value = {
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//
//
//    })
//    @DeleteMapping("/{studyRoomId}/shared-sourcecodes/{sharedSourceCodeId}/feedbacks/{feedbackId}")
//    public ResponseEntity<ApiResponse</**/?>> countFeedbackByLine(@PathVariable Long studyRoomId, @PathVariable Long sharedSourceCodeId, @PathVariable Long feedbackId, @Valid @RequestBody  , @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
//
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(, "피드백을 성공적으로 삭제하였습니다."));
//    }
    //TODO
//    @Operation(summary = "피드백 삭제 API", description = "피드백 삭제 API입니다.")
//    @ApiResponses(value = {
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//
//
//    })
//    @DeleteMapping("/{studyRoomId}/shared-sourcecodes/{sharedSourceCodeId}/feedbacks/{feedbackId}")
//    public ResponseEntity<ApiResponse</**/?>> saveFeedback(@PathVariable Long studyRoomId, @PathVariable Long sharedSourceCodeId, @PathVariable Long feedbackId, @Valid @RequestBody  , @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(, "피드백을 성공적으로 삭제하였습니다."));
//    }

    //TODO
    @Operation(summary = "피드백 수정 API", description = "피드백 수정 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))


    })
    @PostMapping("/{studyRoomId}/shared-sourcecodes/{sharedSourceCodeId}/feedbacks/{feedbackId}")
    public ResponseEntity<ApiResponse</**/?>> saveFeedback(@PathVariable Long studyRoomId, @PathVariable Long sharedSourceCodeId, @PathVariable Long feedbackId, @Valid @RequestBody EditFeedbackRequestForm editFeedbackRequestForm, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        EditedFeedbackResponseDto editedFeedbackResponseDto = feedbackService.edit(studyRoomId, sharedSourceCodeId, feedbackId, editFeedbackRequestForm, userAdapter);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(editedFeedbackResponseDto, "피드백을 성공적으로 수정하였습니다."));
    }
}
