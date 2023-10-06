package algogather.api.controller.studyroom;

import algogather.api.domain.studyroom.StudyRoomVisibility;
import algogather.api.domain.user.UserRepository;
import algogather.api.domain.user.UserRole;

import algogather.api.dto.studyroom.StudyRoomCreateForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import algogather.api.domain.user.User;

import javax.transaction.Transactional;

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
    @DisplayName("스터디방을 성공적으로 생성한다.")
    @WithUserDetails(value = "testUser", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void create_studyroom_success() throws Exception {
        //given
        StudyRoomCreateForm studyRoomCreateForm = StudyRoomCreateForm
                .builder()
                .title("testStudyRoomTitle")
                .description("testDescription")
                .studyRoomVisibility(StudyRoomVisibility.PRIVATE)
                .maxUserCnt(25)
                .build();

        //when
        ResultActions resultActions = mvc.perform(post("/studyrooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studyRoomCreateForm))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("스터디방이 성공적으로 생성되었습니다."))
                .andExpect(jsonPath("$.data.title").value("testStudyRoomTitle"))
                .andExpect(jsonPath("$.data.description").value("testDescription"))
                .andExpect(jsonPath("$.data.studyRoomVisibility").value("PRIVATE"))
                .andExpect(jsonPath("$.data.maxUserCnt").value(25))
                .andExpect(jsonPath("$.data.managerUserId").value("testUser"));
    }

    @Test
    @DisplayName("로그인을 하지 않으면 스터디방을 만들지 못한다.")
    void create_studyroom_not_authenticated_fail() throws Exception {
        //given
        StudyRoomCreateForm studyRoomCreateForm = StudyRoomCreateForm
                .builder()
                .title("testStudyRoomTitle")
                .description("testDescription")
                .studyRoomVisibility(StudyRoomVisibility.PRIVATE)
                .maxUserCnt(25)
                .build();

        //when
        ResultActions resultActions = mvc.perform(post("/studyrooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studyRoomCreateForm))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        //then
        resultActions.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("접근 권한이 없습니다."));
    }

    @Test
    @DisplayName("유저수가 30명을 넘으가면 스터디방을 만들지 못한다.")
    @WithUserDetails(value = "testUser", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void create_studyroom_exceed_max_user_fail() throws Exception {
        //given
        StudyRoomCreateForm studyRoomCreateForm = StudyRoomCreateForm
                .builder()
                .title("testStudyRoomTitle")
                .description("testDescription")
                .studyRoomVisibility(StudyRoomVisibility.PRIVATE)
                .maxUserCnt(31)
                .build();

        //when
        ResultActions resultActions = mvc.perform(post("/studyrooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studyRoomCreateForm))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("검증에 실패하였습니다. data 필드를 참고해주세요"))
                .andExpect(jsonPath("$.data.maxUserCnt").value("유저의 수는 30명 이하이어야 합니다."));
    }
}