package algogather.api.config.security;

import algogather.api.domain.user.User;
import algogather.api.domain.user.UserRepository;
import algogather.api.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetail implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("해당 아이디의 유저를 찾을 수 없습니다."));

        HashSet<UserRole> userRoles = new HashSet<>();
        userRoles.add(user.getRole());

        Set<GrantedAuthority> authorities = userRoles.stream()
                .map((role) -> new SimpleGrantedAuthority(role.getKey()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(userId, user.getPassword(), authorities);
    }
}
