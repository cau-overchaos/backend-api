package algogather.api.domain.studyroom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StudyRoomVisibility {
    PUBLIC("PUBLIC", "공개"), PRIVATE("PRIVATE", "비공개");

    private final String key;
    private final String value;
}
