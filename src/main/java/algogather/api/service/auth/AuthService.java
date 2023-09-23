package algogather.api.service.auth;
import algogather.api.dto.auth.LoginForm;
import algogather.api.dto.auth.SignUpForm;
import algogather.api.domain.user.User;
import algogather.api.domain.user.UserRepository;
import algogather.api.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignUpForm signUpForm) {
        if(userRepository.existsByUserId(signUpForm.getUserId())) {
            //TODO 공통 응답  만들기
            throw new RuntimeException("아이디가 이미 존재합니다.");
        }

        User createdUser = User.builder()
                .userId(signUpForm.getUserId())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .name(signUpForm.getName())
                .profile_image(signUpForm.getProfileImage())
                .judge_account(signUpForm.getJudgeAccount())
                .role(UserRole.USER)
                .build();

        userRepository.save(createdUser);
    }

    public void login(LoginForm loginForm) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUserId(), loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }
}
