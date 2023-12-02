package algogather.api.controller.notification;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.programminglanguage.ProgrammingLanguageListResponseDto;
import algogather.api.service.notification.NotificationService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications/")
@Tag(name = "프로그래밍 언어", description = "프로그래밍 언어 관련 API 입니다.")
public class NotificationController {
    private NotificationService notificationService;

//    public ResponseEntity<ApiResponse<>> findAll(@Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
//
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(, "모든 프로그래밍 언어 목록을 조회했습니다."));
//    }
}
