package algogather.api.controller.auth;

import algogather.api.dto.auth.LoginForm;
import algogather.api.dto.auth.SignUpForm;
import algogather.api.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) {

        authService.login(loginForm);
        return new ResponseEntity<>("유저가 성공적으로 로그인 되었습니다!", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody SignUpForm signUpForm) {

       authService.registerUser(signUpForm);

        return new ResponseEntity<>("유저가 성공적으로 등록되었습니다.", HttpStatus.OK);
    }
}
