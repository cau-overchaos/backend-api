package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoomVisibility;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class StudyRoomCreateForm {
    @NotEmpty(message = "스터디방 제목을 입력해주세요.")
    private String title;

    private String description;

    @NotNull(message = "스터디방 공개 여부를 올바르게 선택해주세요.")
    private StudyRoomVisibility studyRoomVisibility;

    @NotNull(message = "유저 수를 입력해주세요.")
    @Min(value=2, message = "유저의 수는 2명 이상이어야 합니다.")
    @Max(value=30, message = "유저의 수는 30명 이하이어야 합니다.")
    private Integer maxUserCnt;


    @Builder
    public StudyRoomCreateForm(String title, String description, StudyRoomVisibility studyRoomVisibility, Integer maxUserCnt) {
        this.title = title;
        this.description = description;
        this.studyRoomVisibility = studyRoomVisibility;
        this.maxUserCnt = maxUserCnt;
    }
}
