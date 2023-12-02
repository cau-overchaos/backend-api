package algogather.api.controller.notification;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.notification.NotificationListResponseDto;
import algogather.api.dto.programminglanguage.ProgrammingLanguageListResponseDto;
import algogather.api.service.notification.NotificationService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
@Tag(name = "알림", description = "알림 관련 API 입니다.")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "알림 목록 조회 API", description = "알림 목록 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<NotificationListResponseDto>> findAll(@Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {

        NotificationListResponseDto notificationListResponseDto = notificationService.getNotificationListResponseDto(userAdapter);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(notificationListResponseDto, "현재 유저의 알림 목록을 정상적으로 조회했습니다."));
    }
}
