package algogather.api.dto.auth;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class SignUpForm {
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String userId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
    private String profileImage;

    @NotEmpty(message = "온라인 저지 계정이름을 입력해주세요.")
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
