package algogather.api.domain.user;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @AuthenticationPrincipal 시에 UserAdapter를 반환해준다.
 */

@Getter
public class UserAdapter extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserAdapter(User user) {

        super(user.getUserId(), user.getPassword(),
                new HashSet<UserRole>() {{add(user.getRole());}}.stream()
                .map((role) -> new SimpleGrantedAuthority(role.getKey()))
                .collect(Collectors.toSet())
        );
        this.user = user;
    }
}
