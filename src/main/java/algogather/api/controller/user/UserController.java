package algogather.api.controller.user;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.user.ProfileResponseDto;
import algogather.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> getMyProfileInfo(@AuthenticationPrincipal UserAdapter userAdapter) {
        ProfileResponseDto profileInfo = userService.getProfileInfo(userAdapter);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(profileInfo, "프로필 정보를 성공적으로 불러왔습니다."));
    }
}
