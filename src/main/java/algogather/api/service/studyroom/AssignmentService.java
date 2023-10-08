package algogather.api.service.studyroom;

import algogather.api.config.crawler.BojCrawler;
import algogather.api.domain.assignment.AssignmentProblem;
import algogather.api.domain.assignment.AssignmentProblemRepository;
import algogather.api.domain.assignment.AssignmentSolveRepository;
import algogather.api.domain.problem.ProblemProvider;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.problem.ProblemInfoRequestDto;
import algogather.api.dto.studyroom.AssignmentCreateForm;
import algogather.api.dto.studyroom.AssignmentResponseDto;
import algogather.api.dto.studyroom.CreatedAssignmentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentProblemRepository assignmentProblemRepository;
    private final ProblemService problemService;
    private final StudyRoomService studyRoomService;

    public CreatedAssignmentResponseDto createAssignment(UserAdapter userAdapter, AssignmentCreateForm assignmentCreateForm) {

        List<ProblemInfoRequestDto> problemInfoRequestDtoList = assignmentCreateForm.getProblemList();
        List<AssignmentProblem> results = new ArrayList<>();

        // 해당 스터디룸의 관리자만 과제를 생성할 수 있다.
        studyRoomService.throwExceptionIfNotStudyRoomManager(userAdapter, assignmentCreateForm.getStudyRoomId());

        // 문제가 존재하는지 검증
        problemService.validateProblems(problemInfoRequestDtoList);

        for(ProblemInfoRequestDto problemInfo : problemInfoRequestDtoList) {
            AssignmentProblem createdAssignmentProblem = AssignmentProblem.builder()
                    .startDate(assignmentCreateForm.getStartDate())
                    .dueDate(assignmentCreateForm.getDueDate())
                    .studyRoom(studyRoomService.findById(assignmentCreateForm.getStudyRoomId()))
                    .problem(problemService.findByPidAndProvider(problemInfo.getPid(), problemInfo.getProvider()))
                    .build();
            results.add(assignmentProblemRepository.save(createdAssignmentProblem));
        }

        return new CreatedAssignmentResponseDto(results);
    }

    public AssignmentResponseDto getAssignmentList() {
        return null;
    }
}
