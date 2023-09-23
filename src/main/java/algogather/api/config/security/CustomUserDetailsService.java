package algogather.api.config.security;

import algogather.api.domain.user.User;
import algogather.api.domain.user.UserAdapter;
import algogather.api.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("해당 아이디의 유저를 찾을 수 없습니다."));

        return new UserAdapter(user); // @AuthenticationPrincipal 시에 UserAdapter를 반환해준다.
    }
}
