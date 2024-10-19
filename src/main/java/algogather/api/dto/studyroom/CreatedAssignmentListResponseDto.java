package algogather.api.dto.studyroom;

import algogather.api.domain.assignment.AssignmentProblem;
import algogather.api.dto.problem.ProblemResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreatedAssignmentListResponseDto {

    private final List<CreatedAssignment> createdAssignmentList;

    public CreatedAssignmentListResponseDto(List<AssignmentProblem> createdAssignmentList) {
        this.createdAssignmentList = createdAssignmentList.stream()
                .map(createdAssignment -> new CreatedAssignment(createdAssignment))
                .collect(Collectors.toList());
    }

    @Getter
    static class CreatedAssignment {
        @Schema(description = "생성된 과제 고유번호")
        private final Long id;

        @Schema(description = "생성된 문제 정보 (문제 출처, 해당 출처에서 문제 번호)")
        private final ProblemResponseDto problemResponseDto;

        @Schema(description = "생성된 과제와 연관된 스터디방 고유번호")
        private final Long studyRoomId;

        @Schema(description = "과제 시작 날짜")
        private final LocalDateTime startDate;

        @Schema(description = "과제 종료 날짜")
        private final LocalDateTime duetDate;

        public CreatedAssignment(AssignmentProblem assignmentProblem) {
            this.id = assignmentProblem.getId();
            this.problemResponseDto = new ProblemResponseDto(assignmentProblem.getProblem());
            this.studyRoomId = assignmentProblem.getStudyRoom().getId();
            this.startDate = assignmentProblem.getStartDate();
            this.duetDate = assignmentProblem.getDueDate();
        }
    }
}
