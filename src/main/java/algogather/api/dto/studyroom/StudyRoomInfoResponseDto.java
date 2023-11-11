package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.studyroom.StudyRoomVisibility;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StudyRoomInfoResponseDto {
    private Long id;
    private String title;
    private String description;
    private Integer maxUserCnt;
    private List<String> managerUserIdList;

    @Builder
    public StudyRoomInfoResponseDto(Long id, String title, String description, Integer maxUserCnt, List<String> managerUserIdList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.maxUserCnt = maxUserCnt;
        this.managerUserIdList = managerUserIdList;
    }
}
