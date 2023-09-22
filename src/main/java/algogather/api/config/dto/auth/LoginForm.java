package algogather.api.config.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginForm {
    private String userId;
    private String password;

    @Builder
    public LoginForm(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
