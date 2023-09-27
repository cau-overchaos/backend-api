package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.studyroom.StudyRoomVisibility;
import lombok.Getter;

@Getter
public class CreatedStudyRoomResponseDto {
    private Long id;
    private String title;
    private String description;
    private StudyRoomVisibility studyRoomVisibility;
    private Integer maxUserCnt;

    private String managerName;

    public CreatedStudyRoomResponseDto(StudyRoom studyRoom, String managerName) {
        this.id = studyRoom.getId();
        this.title = studyRoom.getTitle();
        this.description = studyRoom.getDescription();
        this.studyRoomVisibility = studyRoom.getStudyRoomVisibility();
        this.maxUserCnt = studyRoom.getMaxUserCnt();
        this.managerName = managerName;
    }
}
