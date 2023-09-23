package algogather.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignUpForm {
    @Schema(description = "아이디")
    @NotEmpty(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9._]*$", message = "알파벳 소문자(a~z), 숫자(0~9), 기호 '_' 만 입력 가능합니다.")
    @Size(min = 4, message = "아이디는 4자 이상이어야 합니다.")
    private String userId;

    @Schema(description = "비밀번호")
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @Schema(description = "이름")
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @Schema(description = "프로필 이미지")
    private String profileImage;

    @Schema(description = "온라인 저지 계정")
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
