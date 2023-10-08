package algogather.api.service.studyroom;

import algogather.api.domain.assignment.AssignmentProblem;
import algogather.api.domain.assignment.AssignmentProblemRepository;
import algogather.api.domain.assignment.AssignmentSolve;
import algogather.api.domain.assignment.AssignmentSolveRepository;
import algogather.api.domain.studyroom.UserStudyRoomRepository;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.problem.ProblemInfoRequestDto;
import algogather.api.dto.studyroom.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import algogather.api.domain.user.User;


import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentProblemRepository assignmentProblemRepository;
    private final AssignmentSolveRepository assignmentSolveRepository;
    private final UserStudyRoomRepository userStudyRoomRepository;

    private final ProblemService problemService;
    private final StudyRoomService studyRoomService;
    private final CrawlingService crawlingService;

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

    //TODO 컨트롤러 만들고 테스트하기
    public AssignmentResponseDto getAssignmentList(UserAdapter userAdapter, Long studyRoomId) {

        // 해당 스터디방의 멤버인지 확인
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId);

        // 해당 스터디방의 과제 목록을 적절한 형식으로 불러온다
            // 마감된 과제이면 DB에만 접근해서 불러온다.
            // 마감되지 않았으면 아래 로직을 따른다
                // 만약 AssignmetSolve에 존재하면 풀었다고 판단
                // 없으면 크롤링해서 정보 가져온다
                    // 크롤링 정보 있으면 AssignmentSolve에 푼날짜와 함께 저장
                    // 없으면 안풀었다고 판단

        List<AssignmentSolve> resultAssignmentSolveList = new ArrayList<>();
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

        // 마감된 과제를 푼 유저와 함께 불러 온다.
        for (AssignmentProblem closedAssignmentProblem : closedAssignmentProblemList) {
            List<AssignmentSolve> assignmentSolveList = assignmentSolveRepository.findByAssignmentProblemId(closedAssignmentProblem.getId());

            resultAssignmentSolveList.addAll(assignmentSolveList);
//            List<User> usersWhoSolvedClosedAssignment = assignmentSolveList.stream().map(AssignmentSolve::getUser).collect(Collectors.toList());

//            assignmentsWithSolvedUsersMap.put(closedAssignmentProblem.getId(), usersWhoSolvedClosedAssignment);
        }


        //TODO 마감되지 않은 과제를 처리한다.
        List<User> userList = userStudyRoomRepository.findUserByStudyRoomId(studyRoomId);

        for (User user : userList) {
            for (AssignmentProblem activeAssignmentProblem : activeAssignmentProblemList) {

                Long assignmentProblemId = activeAssignmentProblem.getId();

                Optional<AssignmentSolve> assignmentSolve = assignmentSolveRepository.existsByUserIdAndAssignmentProblemId(user.getId(), assignmentProblemId);
                if(!assignmentSolve.isEmpty()) { // 푼 기록이 DB에 존재하면 크롤링 안함

                    resultAssignmentSolveList.add(assignmentSolve.get());

//                    if(assignmentsWithSolvedUsersMap.containsKey(assignmentProblemId)) {
//                        List<User> users = assignmentsWithSolvedUsersMap.get(assignmentProblemId);
//                        users.add(user);
//                    }
//                    else {
//                        List<User> users = new ArrayList<>(); users.add(user);
//                        assignmentsWithSolvedUsersMap.put(assignmentProblemId, users);
//                    }
                }
                else { // 푼 기록이 DB에 존재하지 않으면 크롤링을 해서 정보를 가져온다.
                    CompletableFuture<LocalDateTime> localDateTimeCompletableFuture = crawlingService.checkIfUserSolveProblemOnBAEKJOON(user, activeAssignmentProblem);
                    LocalDateTime crawledLocalDateTime = localDateTimeCompletableFuture.join();

                    if(crawledLocalDateTime != null) { // null이 아니면 문제를 과제 기간 안에 풀었다는 의미
                        // DB에 푼 기록을 저장한다.
                        AssignmentSolve newAssignmentSolve = AssignmentSolve.builder()
                                .assignmentProblem(activeAssignmentProblem)
                                .user(user)
                                .solvedDate(crawledLocalDateTime)
                                .build();

                        AssignmentSolve savedAssignmentSolve = assignmentSolveRepository.save(newAssignmentSolve);
                        resultAssignmentSolveList.add(savedAssignmentSolve);
                    }
                    else {
                        // null이면 과제 기간 안에 풀지 않았다는 의미이고 아무 작업도 하지 않는다.
                    }
                }
            }
        }

        /**
         * 스터디방 과제들과 푼 유저를 대응 시켜서 응답으로 보낸다.
         */
        HashMap<Long, List<UserWithSolvedLocalDateDto>> assignmentsWithSolvedUsersMap = new HashMap<>(); // {과제 id, List<UserWithSolvedLocalDate>}

        for (AssignmentProblem assignmentProblem : assignmentProblemListInStudyRoom) { // 응답에서는 스터디방 인원이 문제를 풀었든 안 풀었든 모든 문제에 대한 정보를 주어야함.
            assignmentsWithSolvedUsersMap.put(assignmentProblem.getId(), new ArrayList<>());
        }

        for (AssignmentSolve solve : resultAssignmentSolveList) {
            List<UserWithSolvedLocalDateDto> userWithSolvedLocalDateList = assignmentsWithSolvedUsersMap.get(solve.getAssignmentProblem().getId());
            userWithSolvedLocalDateList.add(new UserWithSolvedLocalDateDto(solve.getUser(), solve.getSolvedDate()));
        }

        Set<Long> keySet = assignmentsWithSolvedUsersMap.keySet();

        List<AssignmentWithSolvedUserDto> assignmentWithSolvedUserDtoList = new ArrayList<>();
        for (Long key : keySet) {
            AssignmentWithSolvedUserDto assignmentWithSolvedUserDto = AssignmentWithSolvedUserDto.builder()
                    .assignmentProblem(assignmentProblemRepository.findById(key).get())
                    .userWithSolvedLocalDateDtoList(assignmentsWithSolvedUsersMap.get(key))
                    .build();
            assignmentWithSolvedUserDtoList.add(assignmentWithSolvedUserDto);
        }

        return new AssignmentResponseDto(assignmentWithSolvedUserDtoList);
    }

    public List<AssignmentProblem> findByStudyRoomId(Long studyRoomId) {

        List<AssignmentProblem> assignmentProblemList = assignmentProblemRepository.findByStudyRoomId(studyRoomId);

        return assignmentProblemList;
    }

}
