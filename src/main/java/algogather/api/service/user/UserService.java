package algogather.api.service.user;

import algogather.api.domain.user.UserAdapter;
import algogather.api.domain.user.UserRepository;
import algogather.api.dto.user.ProfileResponseDto;
import algogather.api.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import algogather.api.domain.user.User;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ProfileResponseDto getProfileInfo(UserAdapter userAdapter) {

        User currentUser = userAdapter.getUser();
        return new ProfileResponseDto(currentUser.getUserId(), currentUser.getName());
    }

    public UserAdapter findByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new UserNotFoundException()
                );
        return new UserAdapter(user);
    }
}
