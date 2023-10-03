package algogather.api.domain.studyroom;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum StudyRoomVisibility {
    PUBLIC("PUBLIC", "공개"), PRIVATE("PRIVATE", "비공개");

    private final String key;
    private final String value;

    /**
     * Json 데이터를 역직렬화 하는 과정을 수동으로 설정
     * 입력값을 대문자로 변환하여 일치하는 enum이 있으면 값 반환
     * 없으면 null 반환, 이를 통해 요청시 잘못된 값 검증할 수 있다.
     */
//    @JsonCreator
//    public static StudyRoomVisibility parsing(String inputValue) {
//        return Stream.of(StudyRoomVisibility.values())
//                .filter(studyRoomVisibility -> studyRoomVisibility.toString().equals(inputValue.toUpperCase()))
//                .findFirst()
//                .orElse(null);
//    }
}
