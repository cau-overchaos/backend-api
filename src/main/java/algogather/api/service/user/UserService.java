package algogather.api.service.user;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.user.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import algogather.api.domain.user.User;

@Service
@RequiredArgsConstructor
public class UserService {

    public ProfileResponseDto getProfileInfo(UserAdapter userAdapter) {

        User currentUser = userAdapter.getUser();
        return new ProfileResponseDto(currentUser.getUserId(), currentUser.getName());
    }
}
