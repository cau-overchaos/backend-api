package algogather.api.controller.auth;

import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.auth.LoginForm;
import algogather.api.dto.auth.SignUpForm;
import algogather.api.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginForm loginForm) {
        authService.login(loginForm);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucess("성공적으로 로그인 되었습니다!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> registerUser(@Valid @RequestBody SignUpForm signUpForm) {

        authService.registerUser(signUpForm);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucess("성공적으로 회원가입 되었습니다!"));
    }
}
