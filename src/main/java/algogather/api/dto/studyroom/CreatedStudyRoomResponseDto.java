package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.studyroom.StudyRoomVisibility;
import algogather.api.dto.programminglanguage.ProgrammingLanguageListResponseDto;
import lombok.Getter;

import java.util.List;


@Getter
public class CreatedStudyRoomResponseDto {
    private Long id;
    private String title;
    private String description;
    private StudyRoomVisibility studyRoomVisibility;
    private Integer maxUserCnt;
    private String originalManagerUserId;
    private ProgrammingLanguageListResponseDto programmingLanguageListResponseDto;

    public CreatedStudyRoomResponseDto(StudyRoom studyRoom, String managerUserId, ProgrammingLanguageListResponseDto programmingLanguageListResponseDto) {
        this.id = studyRoom.getId();
        this.title = studyRoom.getTitle();
        this.description = studyRoom.getDescription();
        this.studyRoomVisibility = studyRoom.getStudyRoomVisibility();
        this.maxUserCnt = studyRoom.getMaxUserCnt();
        this.originalManagerUserId = managerUserId;
        this.programmingLanguageListResponseDto = programmingLanguageListResponseDto;
    }
}
