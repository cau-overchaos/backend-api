package algogather.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginForm {
    @Schema(description = "아이디")
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String userId;

    @Schema(description = "비밀번호")
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
}
