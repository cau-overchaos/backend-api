package algogather.api.dto.user;

import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String userId;
    private String name;

    public ProfileResponseDto(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
