package algogather.api.dto.recruit;

import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.studyroom.StudyRoomVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecruitPostListResponseDto {
    private List<RecruitPostDto> recruitPostDtoList;

    public RecruitPostListResponseDto(List<RecruitPostDto> recruitPostDtoList) {
        this.recruitPostDtoList = recruitPostDtoList;
    }
}
