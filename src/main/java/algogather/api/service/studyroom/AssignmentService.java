package algogather.api.service.studyroom;

import algogather.api.config.crawler.BojCrawler;
import algogather.api.domain.assignment.AssignmentProblem;
import algogather.api.domain.assignment.AssignmentProblemRepository;
import algogather.api.domain.assignment.AssignmentSolveRepository;
import algogather.api.domain.problem.ProblemProvider;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.problem.ProblemInfoRequestDto;
import algogather.api.dto.studyroom.AssignmentCreateForm;
import algogather.api.dto.studyroom.CreatedAssignmentResponseDto;
import algogather.api.exception.NotSupportedProviderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import algogather.api.domain.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentProblemRepository assignmentProblemRepository;
    private final ProblemService problemService;
    private final StudyRoomService studyRoomService;
    private final AssignmentSolveRepository assignmentSolveRepository;

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

    public boolean checkIfUserSolveProblem(User user, AssignmentProblem assignmentProblem) {
        if(assignmentProblem.getProblem().getProvider() == ProblemProvider.BAEKJOON) {
            //AssignmentSolve에서 우선 문제를 푼 기록이 있는지 확인, 있으면 true 반환
            if(assignmentSolveRepository.existsByUserIdAndAssignmentProblemId(user.getId(), assignmentProblem.getId())) {
                return true;
            }
            else {  // 없으면 크롤링해서 정보 가져옴 기간안에 풀었으면 true 반환
                BojCrawler crawler = new BojCrawler();

                try {
                    List<Date> result =  crawler.GetAcceptedDates(user.getJudgeAccount(), assignmentProblem.getProblem().getPid());
                    log.debug("{} results", result.size());
                    for (Date i: result) {
                        System.out.printf("Accepted at %s\n", i.toString());
                    }

                    if(!result.isEmpty()) { // 있으면 true 반환 //TODO 기간 안에 풀었는지 판단 로직 만들기
                        return true;
                    }
                    else { // 없으면 false 반환
                        return false;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            throw new NotSupportedProviderException();
        }
    }
}
