package algogather.api.dto.studyroom;

import algogather.api.domain.assignment.AssignmentProblem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AssignmentWithSolvedUserDto {
    private AssignmentProblem assignmentProblem;

    private List<UserWithSolvedLocalDateDto> userWithSolvedLocalDateDtoList;

    @Builder
    public AssignmentWithSolvedUserDto(AssignmentProblem assignmentProblem, List<UserWithSolvedLocalDateDto> userWithSolvedLocalDateDtoList) {
        this.assignmentProblem = assignmentProblem;
        this.userWithSolvedLocalDateDtoList = userWithSolvedLocalDateDtoList;
    }
}
