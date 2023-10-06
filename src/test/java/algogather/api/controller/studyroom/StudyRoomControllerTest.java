package algogather.api.controller.studyroom;

import algogather.api.domain.problem.*;
import algogather.api.domain.studyroom.*;
import algogather.api.domain.user.UserAdapter;
import algogather.api.domain.user.UserRepository;
import algogather.api.domain.user.UserRole;

import algogather.api.dto.problem.ProblemInfoRequestDto;
import algogather.api.dto.studyroom.AssignmentCreateForm;
import algogather.api.dto.studyroom.CreatedStudyRoomResponseDto;
import algogather.api.dto.studyroom.StudyRoomCreateForm;
import algogather.api.service.studyroom.StudyRoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import algogather.api.domain.user.User;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static algogather.api.domain.problem.ProblemProvider.BAEKJOON;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StudyRoomControllerTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudyRoomService studyRoomService;
    @Autowired
    private StudyRoomRepository studyRoomRepository;
    @Autowired
    private UserStudyRoomRepository userStudyRoomRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private DifficultyRepository difficultyRepository;

    @BeforeEach
    void setTestUser() {
        User user = User.builder()
                .userId("testUser")
                .password("testPassword")
                .name("testName")
                .judge_account("testJudgeAccount")
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
    }

    @Test
    @DisplayName("과제를 성공적으로 생성한다.")
    @WithUserDetails(value = "testUser", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void create_assignment_success() throws Exception {
        //given
        StudyRoomCreateForm studyRoomCreateForm = StudyRoomCreateForm
                .builder()
                .title("testStudyRoomTitle")
                .description("testDescription")
                .studyRoomVisibility(StudyRoomVisibility.PRIVATE)
                .maxUserCnt(25)
                .build();


        //스터디룸 생성
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAdapter userAdapter = (UserAdapter) principal;
        CreatedStudyRoomResponseDto createdStudyRoomResponseDto = studyRoomService.createStudyRoom(userAdapter, studyRoomCreateForm);

        //문제 및 난이도 데이터 저장
        Difficulty testDifficulty1 = Difficulty
                .builder()
                .name("testLevel1")
                .level(0L)
                .provider(BAEKJOON)
                .build();

        Difficulty testDifficulty2 = Difficulty
                .builder()
                .name("testLevel2")
                .level(1L)
                .provider(BAEKJOON)
                .build();

        difficultyRepository.save(testDifficulty1);
        difficultyRepository.save(testDifficulty2);

        Problem testProblem1 = Problem.builder()
                .pid(1L)
                .title("testProblem1")
                .difficulty(testDifficulty1)
                .provider(BAEKJOON)
                .build();

        Problem testProblem2 = Problem.builder()
                .pid(2L)
                .title("testProblem2")
                .difficulty(testDifficulty2)
                .provider(BAEKJOON)
                .build();

        Problem savedProblem1 = problemRepository.save(testProblem1);
        Problem savedProblem2 = problemRepository.save(testProblem2);

        ProblemInfoRequestDto problemInfoRequestDto1 = ProblemInfoRequestDto
                .builder()
                .pid(savedProblem1.getPid())
                .provider(BAEKJOON)
                .build();

        ProblemInfoRequestDto problemInfoRequestDto2 = ProblemInfoRequestDto
                .builder()
                .pid(savedProblem2.getPid())
                .provider(BAEKJOON)
                .build();

        List<ProblemInfoRequestDto> problemInfoRequestDtoList = new ArrayList<>();
        problemInfoRequestDtoList.add(problemInfoRequestDto1);
        problemInfoRequestDtoList.add(problemInfoRequestDto2);

        LocalDateTime testDateTime = LocalDateTime.now();
        AssignmentCreateForm assignmentCreateForm = AssignmentCreateForm.builder()
                .studyRoomId(createdStudyRoomResponseDto.getId())
                .startDate(testDateTime)
                .dueDate(testDateTime)
                .problemList(problemInfoRequestDtoList).
                build();

        //when
        ResultActions resultActions = mvc.perform(post("/studyrooms/" + createdStudyRoomResponseDto.getId() + "/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignmentCreateForm))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("과제가 성공적으로 생성되었습니다."))
                .andExpect(jsonPath("$.data.createdAssignmentList", hasSize(2)))
                .andExpect(jsonPath("$.data.createdAssignmentList[0].problemResponseDto.provider").value("BAEKJOON"))
                .andExpect(jsonPath("$.data.createdAssignmentList[0].problemResponseDto.pid").value(1L))
                .andExpect(jsonPath("$.data.createdAssignmentList[0].problemResponseDto.title").value("testProblem1"))
                .andExpect(jsonPath("$.data.createdAssignmentList[0].problemResponseDto.difficultyLevel").value(0L))
                .andExpect(jsonPath("$.data.createdAssignmentList[0].studyRoomId").value(createdStudyRoomResponseDto.getId()))
                .andExpect(jsonPath("$.data.createdAssignmentList[1].problemResponseDto.provider").value("BAEKJOON"))
                .andExpect(jsonPath("$.data.createdAssignmentList[1].problemResponseDto.pid").value(2L))
                .andExpect(jsonPath("$.data.createdAssignmentList[1].problemResponseDto.title").value("testProblem2"))
                .andExpect(jsonPath("$.data.createdAssignmentList[1].problemResponseDto.difficultyLevel").value(1L))
                .andExpect(jsonPath("$.data.createdAssignmentList[1].studyRoomId").value(createdStudyRoomResponseDto.getId()));
    }

    @Test
    @DisplayName("스터디방 멤버가 아니면 과제를 생성하지 못한다.")
    @WithUserDetails(value = "testUser", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void create_assignment_not_studyroom_memeber_fail() throws Exception {
        //given

        // 스터디방을 만들고 관리자와 연관시킨다.
        StudyRoom studyRoom = StudyRoom.builder()
                .title("testStudyRoomTitle")
                .description("testDescription")
                .studyRoomVisibility(StudyRoomVisibility.PRIVATE)
                .maxUserCnt(25)
                .build();

        User manager = User.builder()
                .userId("testManager")
                .password("testPassword")
                .name("ManagerName")
                .judge_account("ManagerJudgeAccount")
                .role(UserRole.USER)
                .build();

        UserStudyRoom userStudyRoom = UserStudyRoom.builder()
                .studyRoom(studyRoom)
                .user(manager)
                .role(StudyRoomRole.MANAGER)
                .build();

        userRepository.save(manager);
        studyRoomRepository.save(studyRoom);
        userStudyRoomRepository.save(userStudyRoom);


        //문제 및 난이도 데이터 저장
        Difficulty testDifficulty1 = Difficulty
                .builder()
                .name("testLevel1")
                .level(0L)
                .provider(BAEKJOON)
                .build();

        Difficulty testDifficulty2 = Difficulty
                .builder()
                .name("testLevel2")
                .level(1L)
                .provider(BAEKJOON)
                .build();

        difficultyRepository.save(testDifficulty1);
        difficultyRepository.save(testDifficulty2);

        Problem testProblem1 = Problem.builder()
                .pid(1L)
                .title("testProblem1")
                .difficulty(testDifficulty1)
                .provider(BAEKJOON)
                .build();

        Problem testProblem2 = Problem.builder()
                .pid(2L)
                .title("testProblem2")
                .difficulty(testDifficulty2)
                .provider(BAEKJOON)
                .build();

        Problem savedProblem1 = problemRepository.save(testProblem1);
        Problem savedProblem2 = problemRepository.save(testProblem2);

        ProblemInfoRequestDto problemInfoRequestDto1 = ProblemInfoRequestDto
                .builder()
                .pid(savedProblem1.getPid())
                .provider(BAEKJOON)
                .build();

        ProblemInfoRequestDto problemInfoRequestDto2 = ProblemInfoRequestDto
                .builder()
                .pid(savedProblem2.getPid())
                .provider(BAEKJOON)
                .build();

        List<ProblemInfoRequestDto> problemInfoRequestDtoList = new ArrayList<>();
        problemInfoRequestDtoList.add(problemInfoRequestDto1);
        problemInfoRequestDtoList.add(problemInfoRequestDto2);

        LocalDateTime testDateTime = LocalDateTime.now();
        AssignmentCreateForm assignmentCreateForm = AssignmentCreateForm.builder()
                .studyRoomId(studyRoom.getId())
                .startDate(testDateTime)
                .dueDate(testDateTime)
                .problemList(problemInfoRequestDtoList).
                build();

        //when
        ResultActions resultActions = mvc.perform(post("/studyrooms/" + studyRoom.getId() + "/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentCreateForm))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("해당 스터디방의 멤버가 아닙니다."));
    }

    @Test
    @DisplayName("스터디방 매니저가 아니면 과제를 생성하지 못한다.")
    @WithUserDetails(value = "testUser", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void create_assignment_not_studyroom_manager_fail() throws Exception {
        //given

        // 스터디방을 만들고 관리자와 연관시킨다.
        StudyRoom studyRoom = StudyRoom.builder()
                .title("testStudyRoomTitle")
                .description("testDescription")
                .studyRoomVisibility(StudyRoomVisibility.PRIVATE)
                .maxUserCnt(25)
                .build();

        User manager = User.builder()
                .userId("testManager")
                .password("testPassword")
                .name("ManagerName")
                .judge_account("ManagerJudgeAccount")
                .role(UserRole.USER)
                .build();

        UserStudyRoom userStudyRoom = UserStudyRoom.builder()
                .studyRoom(studyRoom)
                .user(manager)
                .role(StudyRoomRole.MANAGER)
                .build();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAdapter userAdapter = (UserAdapter) principal;

        UserStudyRoom userStudyRoomNotManager = UserStudyRoom.builder()
                .studyRoom(studyRoom)
                .user(userAdapter.getUser())
                .role(StudyRoomRole.USER)
                .build();

        userRepository.save(manager);
        studyRoomRepository.save(studyRoom);
        userStudyRoomRepository.save(userStudyRoom);
        userStudyRoomRepository.save(userStudyRoomNotManager);


        //문제 및 난이도 데이터 저장
        Difficulty testDifficulty1 = Difficulty
                .builder()
                .name("testLevel1")
                .level(0L)
                .provider(BAEKJOON)
                .build();

        Difficulty testDifficulty2 = Difficulty
                .builder()
                .name("testLevel2")
                .level(1L)
                .provider(BAEKJOON)
                .build();

        difficultyRepository.save(testDifficulty1);
        difficultyRepository.save(testDifficulty2);

        Problem testProblem1 = Problem.builder()
                .pid(1L)
                .title("testProblem1")
                .difficulty(testDifficulty1)
                .provider(BAEKJOON)
                .build();

        Problem testProblem2 = Problem.builder()
                .pid(2L)
                .title("testProblem2")
                .difficulty(testDifficulty2)
                .provider(BAEKJOON)
                .build();

        Problem savedProblem1 = problemRepository.save(testProblem1);
        Problem savedProblem2 = problemRepository.save(testProblem2);

        ProblemInfoRequestDto problemInfoRequestDto1 = ProblemInfoRequestDto
                .builder()
                .pid(savedProblem1.getPid())
                .provider(BAEKJOON)
                .build();

        ProblemInfoRequestDto problemInfoRequestDto2 = ProblemInfoRequestDto
                .builder()
                .pid(savedProblem2.getPid())
                .provider(BAEKJOON)
                .build();

        List<ProblemInfoRequestDto> problemInfoRequestDtoList = new ArrayList<>();
        problemInfoRequestDtoList.add(problemInfoRequestDto1);
        problemInfoRequestDtoList.add(problemInfoRequestDto2);

        LocalDateTime testDateTime = LocalDateTime.now();
        AssignmentCreateForm assignmentCreateForm = AssignmentCreateForm.builder()
                .studyRoomId(studyRoom.getId())
                .startDate(testDateTime)
                .dueDate(testDateTime)
                .problemList(problemInfoRequestDtoList).
                build();

        //when
        ResultActions resultActions = mvc.perform(post("/studyrooms/" + studyRoom.getId() + "/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentCreateForm))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("해당 스터디룸의 관리자가 아닙니다!"));
    }

    @Test
    @DisplayName("DB에 존재하지 않는 문제를 과제로 넘겨주면 예외 발생한다.")
    @WithUserDetails(value = "testUser", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void create_assignment_not_existing_problem_fail() throws Exception {
        //given
        StudyRoomCreateForm studyRoomCreateForm = StudyRoomCreateForm
                .builder()
                .title("testStudyRoomTitle")
                .description("testDescription")
                .studyRoomVisibility(StudyRoomVisibility.PRIVATE)
                .maxUserCnt(25)
                .build();


        //스터디룸 생성
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAdapter userAdapter = (UserAdapter) principal;
        CreatedStudyRoomResponseDto createdStudyRoomResponseDto = studyRoomService.createStudyRoom(userAdapter, studyRoomCreateForm);

        //문제 및 난이도 데이터 저장
        Difficulty testDifficulty1 = Difficulty
                .builder()
                .name("testLevel1")
                .level(0L)
                .provider(BAEKJOON)
                .build();

        Difficulty testDifficulty2 = Difficulty
                .builder()
                .name("testLevel2")
                .level(1L)
                .provider(BAEKJOON)
                .build();

        difficultyRepository.save(testDifficulty1);
        difficultyRepository.save(testDifficulty2);

        Problem testProblem1 = Problem.builder()
                .pid(1L)
                .title("testProblem1")
                .difficulty(testDifficulty1)
                .provider(BAEKJOON)
                .build();

        Problem testProblem2 = Problem.builder()
                .pid(2L)
                .title("testProblem2")
                .difficulty(testDifficulty2)
                .provider(BAEKJOON)
                .build();

        Problem savedProblem1 = problemRepository.save(testProblem1);
        Problem savedProblem2 = problemRepository.save(testProblem2);

        ProblemInfoRequestDto problemInfoRequestDto1 = ProblemInfoRequestDto
                .builder()
                .pid(savedProblem1.getPid())
                .provider(BAEKJOON)
                .build();

        ProblemInfoRequestDto problemInfoRequestDto2 = ProblemInfoRequestDto
                .builder()
                .pid(savedProblem2.getPid())
                .provider(BAEKJOON)
                .build();

        ProblemInfoRequestDto problemInfoRequestDtoWithNotExistingProblem = ProblemInfoRequestDto
                .builder()
                .pid(9999L)
                .provider(BAEKJOON)
                .build();

        List<ProblemInfoRequestDto> problemInfoRequestDtoList = new ArrayList<>();
        problemInfoRequestDtoList.add(problemInfoRequestDto1);
        problemInfoRequestDtoList.add(problemInfoRequestDto2);
        problemInfoRequestDtoList.add(problemInfoRequestDtoWithNotExistingProblem);

        LocalDateTime testDateTime = LocalDateTime.now();
        AssignmentCreateForm assignmentCreateForm = AssignmentCreateForm.builder()
                .studyRoomId(createdStudyRoomResponseDto.getId())
                .startDate(testDateTime)
                .dueDate(testDateTime)
                .problemList(problemInfoRequestDtoList).
                build();

        //when
        ResultActions resultActions = mvc.perform(post("/studyrooms/" + createdStudyRoomResponseDto.getId() + "/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentCreateForm))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(problemInfoRequestDtoWithNotExistingProblem.getProvider().getValue() + "의 " + problemInfoRequestDtoWithNotExistingProblem.getPid() + "번 문제를 찾을 수 없습니다."));
    }
}