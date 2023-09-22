package algogather.api.domain.studyroom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StudyRoomRole {
    USER("USER", "유저"), ADMIN("MANAGER", "관리자");

    private final String key;
    private final String value;
}
