package algogather.api.dto.studyroom;

import algogather.api.dto.problem.ProblemResponseDto;
import algogather.api.dto.user.ProfileResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class AssignmentResponseDto {

    List<AssignmentDto> assignmentDtoList;

    @Getter
    static class AssignmentDto {
        private Long assignmentId;

        private ProblemResponseDto problemResponseDto;

        private List<ProfileResponseDto> profileResponseDto; // 푼 유저 목록을 담는다.
    }
}
