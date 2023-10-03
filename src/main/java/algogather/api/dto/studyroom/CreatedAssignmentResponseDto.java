package algogather.api.dto.studyroom;

import algogather.api.domain.assignment.AssignmentProblem;
import algogather.api.domain.problem.ProblemProvider;
import algogather.api.dto.problem.ProblemInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreatedAssignmentResponseDto {

    private List<CreatedAssignment> createdAssignmentList;

    public CreatedAssignmentResponseDto(List<AssignmentProblem> createdAssignmentList) {
        this.createdAssignmentList = createdAssignmentList.stream()
                .map(createdAssignment -> new CreatedAssignment(createdAssignment)).collect(Collectors.toList());
    }

    static class CreatedAssignment {
        private Long id;
        private ProblemInfoDto problemInfoDto;
        private Long studyRoomId;
        private LocalDateTime startDate;
        private LocalDateTime duetDate;

        public CreatedAssignment(AssignmentProblem assignmentProblem) {
            this.id = assignmentProblem.getId();
            this.problemInfoDto = new ProblemInfoDto(assignmentProblem.getProblem().getProvider(), assignmentProblem.getProblem().getPid());
            this.studyRoomId = assignmentProblem.getStudyRoom().getId();
            this.startDate = assignmentProblem.getStartDate();
            this.duetDate = assignmentProblem.getDueDate();
        }
    }
}
