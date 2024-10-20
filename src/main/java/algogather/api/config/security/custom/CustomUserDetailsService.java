package algogather.api.config.security.custom;

import algogather.api.domain.user.User;
import algogather.api.domain.user.UserAdapter;
import algogather.api.domain.user.UserRepository;
import algogather.api.exception.user.UserNotFoundException;
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
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException());

        return new UserAdapter(user); // @AuthenticationPrincipal 시에 UserAdapter를 반환해준다.
    }
}
