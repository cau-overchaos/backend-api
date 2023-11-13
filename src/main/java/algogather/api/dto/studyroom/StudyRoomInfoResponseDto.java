package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.studyroom.StudyRoomVisibility;
import algogather.api.dto.programminglanguage.ProgrammingLanguageListResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StudyRoomInfoResponseDto {
    private Long id;
    private String title;
    private String description;
    private Integer curUserCnt;
    private Integer maxUserCnt;
    private List<String> managerUserIdList;
    private ProgrammingLanguageListResponseDto programmingLanguageListResponseDto;

    @Builder

    public StudyRoomInfoResponseDto(Long id, String title, String description, Integer curUserCnt, Integer maxUserCnt, List<String> managerUserIdList, ProgrammingLanguageListResponseDto programmingLanguageListResponseDto) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.curUserCnt = curUserCnt;
        this.maxUserCnt = maxUserCnt;
        this.managerUserIdList = managerUserIdList;
        this.programmingLanguageListResponseDto = programmingLanguageListResponseDto;
    }
}
