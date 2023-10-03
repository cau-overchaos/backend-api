package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoomVisibility;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class StudyRoomCreateForm {
    @NotEmpty(message = "스터디방 제목을 입력해주세요.")
    private String title;

    private String description;

    @NotEmpty(message = "스터디방 공개여부를 선택해주세요.")
    private StudyRoomVisibility studyRoomVisibility;

    @NotEmpty(message = "유저의 수는 2~30명의 범위로 입력해주세요")
    @Size(min = 2, max = 30)
    private Integer maxUserCnt;

}
