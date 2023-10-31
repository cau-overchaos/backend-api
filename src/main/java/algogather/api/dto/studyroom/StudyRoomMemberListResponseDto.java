package algogather.api.dto.studyroom;

import lombok.Getter;

import java.util.List;


@Getter
public class StudyRoomMemberListResponseDto {

    List<StudyRoomMemberInfoDto> studyRoomMemberDtoList;

    public StudyRoomMemberListResponseDto(List<StudyRoomMemberInfoDto> studyRoomMemberDtoList) {
        this.studyRoomMemberDtoList = studyRoomMemberDtoList;
    }
}
