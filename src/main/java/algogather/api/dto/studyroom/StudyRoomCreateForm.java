package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoomVisibility;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;
import java.util.Set;

@Getter
public class StudyRoomCreateForm {
    @NotEmpty(message = "스터디방 제목을 입력해주세요.")
    @Size(max = 50, message = "제목은 50자 이하여야합니다.")
    private String title;


    @Size(max = 500, message = "내용은 500자 이하여야합니다")
    private String description;

    @NotNull(message = "스터디방 공개 여부를 올바르게 선택해주세요.")
    private StudyRoomVisibility studyRoomVisibility;

    @NotEmpty(message = "프로그래밍 언어를 선택해주세요.")
    private Set<Long> programmingLanguageList;

    @NotNull(message = "유저 수를 입력해주세요.")
    @Min(value=2, message = "유저의 수는 2명 이상이어야 합니다.")
    @Max(value=30, message = "유저의 수는 30명 이하이어야 합니다.")
    private Integer maxUserCnt;

    @Builder
    public StudyRoomCreateForm(String title, String description, StudyRoomVisibility studyRoomVisibility, Set<Long> programmingLanguageList, Integer maxUserCnt) {
        this.title = title;
        this.description = description;
        this.studyRoomVisibility = studyRoomVisibility;
        this.programmingLanguageList = programmingLanguageList;
        this.maxUserCnt = maxUserCnt;
    }
}
