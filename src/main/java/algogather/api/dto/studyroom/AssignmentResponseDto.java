package algogather.api.dto.studyroom;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AssignmentResponseDto {

    private List<AssignmentInfo> assignmentInfoList;

    public AssignmentResponseDto(List<AssignmentWithSolvedUserDto> assignmentWithSolvedUserDtoList){
        this.assignmentInfoList = assignmentWithSolvedUserDtoList.stream()
                .map((assignmentWithSolvedUserDto) ->
                            new AssignmentInfo(
                                    assignmentWithSolvedUserDto.getAssignmentProblem().getProblem().getPid(),
                                    assignmentWithSolvedUserDto.getAssignmentProblem().getProblem().getTitle(),
                                    assignmentWithSolvedUserDto.getAssignmentProblem().getStartDate(),
                                    assignmentWithSolvedUserDto.getAssignmentProblem().getDueDate(),
                                    assignmentWithSolvedUserDto.getAssignmentProblem().getProblem().getDifficulty().getLevel(),
                                    assignmentWithSolvedUserDto.getAssignmentProblem().getProblem().getDifficulty().getName(),

                                    assignmentWithSolvedUserDto.getUserWithSolvedLocalDateDtoList().stream()
                                            .map(userWithSolvedLocalDateDto -> new SolvedUserInfo(
                                                    userWithSolvedLocalDateDto.getUser().getUserId(),
                                                    userWithSolvedLocalDateDto.getUser().getName(),
                                                    userWithSolvedLocalDateDto.getUser().getJudgeAccount(),
                                                    userWithSolvedLocalDateDto.getSolvedDate())
                                            ).collect(Collectors.toList())

                            )
                ).collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    static class AssignmentInfo {
        private Long problemPid;
        private String problemTitle;
        private LocalDateTime startDate;
        private LocalDateTime dueDate;
        private Long problemDifficultyLevel;
        private String problemDifficultyName;
        private List<SolvedUserInfo> solvedUserInfoList;
    }

    @Getter
    @AllArgsConstructor
    static class SolvedUserInfo {
        private String userId;
        private String name;
        private String judgeAccount;
        private LocalDateTime solvedDate;
    }

}
