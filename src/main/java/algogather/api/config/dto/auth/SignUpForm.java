package algogather.api.config.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpForm {
    private String userId;
    private String password;
    private String name;
    private String profileImage;
    private String judgeAccount;

    @Builder
    public SignUpForm(String userId, String password, String name, String profileImage, String judgeAccount) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
        this.judgeAccount = judgeAccount;
    }
}
