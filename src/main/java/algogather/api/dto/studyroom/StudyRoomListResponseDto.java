package algogather.api.dto.studyroom;

import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.studyroom.StudyRoomVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StudyRoomListResponseDto {
    private List<StudyRoomDto> studyRoomList;

    public StudyRoomListResponseDto(List<StudyRoom> studyRoomList) {
        this.studyRoomList = studyRoomList.stream()
                .map(studyRoom -> new StudyRoomDto(studyRoom))
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    public static class StudyRoomDto {
        private Long id;
        private String title;
        private StudyRoomVisibility studyRoomVisibility;
        private Integer maxUserCnt;

        public StudyRoomDto(StudyRoom studyRoom) {
            this.id = studyRoom.getId();
            this.title = studyRoom.getTitle();
            this.studyRoomVisibility = studyRoom.getStudyRoomVisibility();
            this.maxUserCnt = studyRoom.getMaxUserCnt();
        }
    }
}
