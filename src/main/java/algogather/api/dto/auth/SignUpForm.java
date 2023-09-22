package algogather.api.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpForm {
    private String userId;
    private String password;
    private String name;
    private String profile_image;
    private String judge_account;

    @Builder
    public SignUpForm(String userId, String password, String name, String profile_image, String judge_account) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.profile_image = profile_image;
        this.judge_account = judge_account;
    }
}
