package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoomVisibility;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class StudyRoomCreateForm {

    @NotEmpty
    private String title;

    private String description;

    @NotEmpty
    private StudyRoomVisibility studyRoomVisibility;

    @NotEmpty
    @Size(min = 2, max = 30)
    private Integer maxUserCnt;

}
