package algogather.api.service.studyroom;

import algogather.api.domain.assignment.AssignmentProblem;
import algogather.api.domain.assignment.AssignmentProblemRepository;
import algogather.api.domain.assignment.AssignmentSolve;
import algogather.api.domain.assignment.AssignmentSolveRepository;
import algogather.api.domain.studyroom.UserStudyRoomRepository;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.problem.ProblemInfoRequestDto;
import algogather.api.dto.studyroom.AssignmentCreateForm;
import algogather.api.dto.studyroom.AssignmentResponseDto;
import algogather.api.dto.studyroom.CreatedAssignmentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import algogather.api.domain.user.User;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentProblemRepository assignmentProblemRepository;
    private final AssignmentSolveRepository assignmentSolveRepository;
    private final UserStudyRoomRepository userStudyRoomRepository;

    private final ProblemService problemService;
    private final StudyRoomService studyRoomService;

    public CreatedAssignmentResponseDto createAssignment(UserAdapter userAdapter, Long studyRoomId, AssignmentCreateForm assignmentCreateForm) {

        List<ProblemInfoRequestDto> problemInfoRequestDtoList = assignmentCreateForm.getProblemList();
        List<AssignmentProblem> results = new ArrayList<>();

        // 해당 스터디룸의 관리자만 과제를 생성할 수 있다.
        studyRoomService.throwExceptionIfNotStudyRoomManager(userAdapter, studyRoomId);

        // 문제가 존재하는지 검증
        problemService.validateProblems(problemInfoRequestDtoList);

        for(ProblemInfoRequestDto problemInfo : problemInfoRequestDtoList) {
            AssignmentProblem createdAssignmentProblem = AssignmentProblem.builder()
                    .startDate(assignmentCreateForm.getStartDate())
                    .dueDate(assignmentCreateForm.getDueDate())
                    .studyRoom(studyRoomService.findById(studyRoomId))
                    .problem(problemService.findByPidAndProvider(problemInfo.getPid(), problemInfo.getProvider()))
                    .build();
            results.add(assignmentProblemRepository.save(createdAssignmentProblem));
        }

        return new CreatedAssignmentResponseDto(results);
    }

    public AssignmentResponseDto getAssignmentList(UserAdapter userAdapter, Long studyRoomId) {

        // 해당 스터디방의 멤버인지 확인
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId);

        // 해당 스터디방의 과제 목록을 적절한 형식으로 불러온다
            // 마감된 과제이면 DB에만 접근해서 불러온다.
            // 마감되지 않았으면 아래 로직을 따른다
                // 만약 AssignmetSolve에 존재하면 풀었다고 판단
                // 없으면 크롤링해서 정보 가져온다.

        List<AssignmentProblem> assignmentProblemListInStudyRoom = findByStudyRoomId(studyRoomId);
        List<AssignmentProblem> closedAssignmentProblemList = new ArrayList<>();
        List<AssignmentProblem> activeAssignmentProblemList = new ArrayList<>();

        // 마감된 과제와 마감되지 않은 과제를 구분한다.
        for (AssignmentProblem assignmentProblem : assignmentProblemListInStudyRoom) {
            if(LocalDateTime.now().isAfter(assignmentProblem.getDueDate())) {
                closedAssignmentProblemList.add(assignmentProblem);
            }
            else {
                activeAssignmentProblemList.add(assignmentProblem);
            }
        }

        HashMap<Long, List<User>> assignmentsWithSolvedUsersMap = new HashMap<>(); // {과제 id, List<User>}

        // 마감된 과제를 푼 유저와 함께 불러 온다.
        for (AssignmentProblem closedAssignmentProblem : closedAssignmentProblemList) {
            List<AssignmentSolve> assignmentSolveList = assignmentSolveRepository.findByAssignmentProblemId(closedAssignmentProblem.getId());

            List<User> usersWhoSolvedClosedAssignment = assignmentSolveList.stream().map(AssignmentSolve::getUser).collect(Collectors.toList());

            assignmentsWithSolvedUsersMap.put(closedAssignmentProblem.getId(), usersWhoSolvedClosedAssignment);
        }


        //TODO 마감되지 않은 과제를 처리한다.
        List<User> userList = userStudyRoomRepository.findUserByStudyRoomId(studyRoomId);

        for (User user : userList) {
            for (AssignmentProblem activeAssignmentProblem : activeAssignmentProblemList) {

                // 푼 기록이 DB에 존재하면 assignmentsWithSolvedUserMap에 저장

                // 푼 기록이 존재하지 않으면 크롤링해서 정보 가져온다.

            }
        }


        // 반환한다

        return null;
    }

    public List<AssignmentProblem> findByStudyRoomId(Long studyRoomId) {

        List<AssignmentProblem> assignmentProblemList = assignmentProblemRepository.findByStudyRoomId(studyRoomId);

        return assignmentProblemList;
    }
}
