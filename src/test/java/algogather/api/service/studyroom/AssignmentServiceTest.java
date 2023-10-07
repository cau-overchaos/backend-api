package algogather.api.service.studyroom;

import algogather.api.config.crawler.BojCrawlerThread;
import algogather.api.domain.assignment.AssignmentProblem;
import algogather.api.domain.assignment.AssignmentProblemRepository;
import algogather.api.domain.problem.Problem;
import algogather.api.domain.problem.ProblemProvider;
import algogather.api.domain.problem.ProblemRepository;
import algogather.api.domain.user.UserRepository;
import algogather.api.domain.user.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import algogather.api.domain.user.User;
import org.springframework.util.StopWatch;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Slf4j
class AssignmentServiceTest {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ProblemRepository problemRepository;
    @Autowired
    protected AssignmentService assignmentService;
    @Autowired
    protected AssignmentProblemRepository assignmentProblemRepository;

    @Test
    void checkIfUserSolveProblemOnce() { // 1회 호출 약 0.4초
        User testUser = User.builder()
                .userId("testUserId")
                .name("testUserName")
                .password("testPassword")
                .judge_account("boulce")
                .role(UserRole.USER)
                .build();
        User savedUser = userRepository.save(testUser);

        Problem problem = Problem.builder()
                .pid(2000L)
                .provider(ProblemProvider.BAEKJOON)
                .build();

        Problem savedProblem = problemRepository.save(problem);

        AssignmentProblem assignmentProblem = AssignmentProblem.builder()
                .problem(savedProblem)
                .build();

        AssignmentProblem savedAssignmentProblem = assignmentProblemRepository.save(assignmentProblem);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        assignmentService.checkIfUserSolveProblem(savedUser, savedAssignmentProblem);
//        log.info("problme: {}, solved: {}", savedAssignmentProblem.getProblem().getPid(), assignmentService.checkIfUserSolveProblem(savedUser, savedAssignmentProblem));

        stopWatch.stop();
        log.info("time: {}", stopWatch.getTotalTimeSeconds());
    }

    @Test
    void checkIfUserSolveProblem100times() { // 100회 호출 약 9초
        User testUser = User.builder()
                .userId("testUserId")
                .name("testUserName")
                .password("testPassword")
                .judge_account("boulce")
                .role(UserRole.USER)
                .build();
        User savedUser = userRepository.save(testUser);

        Problem problem = Problem.builder()
                .pid(2000L)
                .provider(ProblemProvider.BAEKJOON)
                .build();

        Problem savedProblem = problemRepository.save(problem);

        AssignmentProblem assignmentProblem = AssignmentProblem.builder()
                .problem(savedProblem)
                .build();

        AssignmentProblem savedAssignmentProblem = assignmentProblemRepository.save(assignmentProblem);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for(int i = 0; i <100; i++) {
            assignmentService.checkIfUserSolveProblem(savedUser, savedAssignmentProblem);
//            log.info("problme: {}, solved: {}", savedAssignmentProblem.getProblem().getPid(), assignmentService.checkIfUserSolveProblem(savedUser, savedAssignmentProblem));
        }

        stopWatch.stop();
        log.info("time: {}", stopWatch.getTotalTimeSeconds());
    }

    @Test
    void checkIfUserSolveProblem100timesMultiThreading() { // 쓰레드 100개로 100회 호출 약 2초
        User testUser = User.builder()
                .userId("testUserId")
                .name("testUserName")
                .password("testPassword")
                .judge_account("boulce")
                .role(UserRole.USER)
                .build();
        User savedUser = userRepository.save(testUser);

        Problem problem = Problem.builder()
                .pid(1000L)
                .provider(ProblemProvider.BAEKJOON)
                .build();

        Problem savedProblem = problemRepository.save(problem);

        AssignmentProblem assignmentProblem = AssignmentProblem.builder()
                .problem(savedProblem)
                .build();

        AssignmentProblem savedAssignmentProblem = assignmentProblemRepository.save(assignmentProblem);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<BojCrawlerThread> bojCrawlerThreadArrayList = new ArrayList<>();
        for(int i = 0; i <50; i++) {
            BojCrawlerThread bojCrawlerThread = new BojCrawlerThread(savedUser.getJudgeAccount(), assignmentProblem.getProblem().getPid(), i);
            bojCrawlerThreadArrayList.add(bojCrawlerThread);
        }

        for(int i = 0; i <50; i++) {
            BojCrawlerThread bojCrawlerThread = new BojCrawlerThread("smmaker118", assignmentProblem.getProblem().getPid(), i);
            bojCrawlerThreadArrayList.add(bojCrawlerThread);
        }

        for(BojCrawlerThread b : bojCrawlerThreadArrayList) {
            try {
                b.getThread().join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for(BojCrawlerThread b : bojCrawlerThreadArrayList) {
            for (Date resultAcceptedDate : b.getResultAcceptedDates()) {
                log.info("{}", resultAcceptedDate);
            }
        }

        stopWatch.stop();
        log.info("time: {}", stopWatch.getTotalTimeSeconds());
    }
}