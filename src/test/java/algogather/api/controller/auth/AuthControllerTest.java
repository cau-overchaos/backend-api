package algogather.api.controller.auth;

import algogather.api.domain.user.UserRepository;
import algogather.api.dto.auth.LoginForm;
import algogather.api.dto.auth.SignUpForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입을 하면 유저가 생성된다.")
    void signup_success_test() throws Exception {
        //given
        SignUpForm signUpForm = SignUpForm.builder().userId("testid")
                .password("testPassword")
                .name("testUser")
                .profileImage("testProfileImg")
                .judgeAccount("testJudgeAccount")
                .build();


        //when
        ResultActions resultActions = mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpForm))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());


        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("성공적으로 회원가입 되었습니다!"));
    }

    @Test
    @DisplayName("회원가입을 할때 유저가 이미 존재하면 실패한다")
    void signup_fail_alreadyExistingUser_test() throws Exception {
        //given
        SignUpForm signUpForm = SignUpForm.builder().userId("testid")
                .password("testPassword")
                .name("testUser")
                .profileImage("testProfileImg")
                .judgeAccount("testJudgeAccount")
                .build();

        ResultActions resultAction1 = mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpForm))
                        .accept(MediaType.APPLICATION_JSON)).andDo(print());

        //when
        ResultActions resultAction2 = mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpForm))
                        .accept(MediaType.APPLICATION_JSON)).andDo(print());


        //then
        resultAction2
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("이미 존재하는 아이디입니다."));
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success_test() throws Exception {
        //given
        SignUpForm signUpForm = SignUpForm.builder().userId("testid")
                .password("testPassword")
                .name("testUser")
                .profileImage("testProfileImg")
                .judgeAccount("testJudgeAccount")
                .build();

        LoginForm loginForm = LoginForm.builder().userId("testid")
                .password("testPassword")
                .build();

        ResultActions resultActionSingUp = mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpForm))
                        .accept(MediaType.APPLICATION_JSON)).andDo(print());

        //when
        ResultActions resultActionLogin = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginForm))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        //then
        resultActionLogin
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("성공적으로 로그인 되었습니다!"));
    }

    @Test
    @DisplayName("로그인 실패")
    void login_fail_test() throws Exception {
        //given
        LoginForm loginForm = LoginForm.builder().userId("testid")
                .password("testPassword")
                .build();

        //when
        ResultActions resultActionLogin = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginForm))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        //then
        resultActionLogin
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("해당 유저 아이디가 존재하지 않습니다."));
    }
}