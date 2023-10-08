package algogather.api.service.studyroom;

import algogather.api.domain.assignment.AssignmentProblem;
import algogather.api.domain.assignment.AssignmentProblemRepository;
import algogather.api.domain.problem.Problem;
import algogather.api.domain.problem.ProblemProvider;
import algogather.api.domain.problem.ProblemRepository;
import algogather.api.domain.user.UserRepository;
import algogather.api.domain.user.UserRole;
import algogather.api.exception.AsyncException;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@SpringBootTest
@Transactional
@Slf4j
class AssignmentServiceTest {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ProblemRepository problemRepository;
    @Autowired
    protected CrawlingService crawlingService;
    @Autowired
    protected AssignmentProblemRepository assignmentProblemRepository;

    @Test
    void checkIfUserSolveProblemOnceWithSpringAsync() throws ExecutionException, InterruptedException { // @Async 적용 1회 호출 약 0.4초
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

        List<CompletableFuture<Boolean>> result = new ArrayList<>();

        CompletableFuture<Boolean> booleanCompletableFuture = crawlingService.checkIfUserSolveProblem(savedUser, savedAssignmentProblem);

        booleanCompletableFuture.exceptionally(
                throwable -> {
                    throw new AsyncException("비동기 멀티스레딩 중 예외가 발생하였습니다.");
                }
        );

        result.add(booleanCompletableFuture);

        List<Boolean> booleanList = CompletableFuture.allOf(result.toArray(new CompletableFuture[0]))
                .thenApply(Void -> result.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        stopWatch.stop();
        log.info("==============time: {}================", stopWatch.getTotalTimeSeconds());

        for (Boolean b : booleanList) {
            log.info("problem: {}, solved: {}", savedAssignmentProblem.getProblem().getPid(), b);
        }
    }

    @Test
    void checkIfUserSolveProblem50timesWithSpringAsync() throws ExecutionException, InterruptedException { // @Async 적용 50회 호출 약 1초
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

        List<CompletableFuture<Boolean>> result = new ArrayList<>();
        for(int i = 0; i <50; i++) {
            CompletableFuture<Boolean> booleanCompletableFuture = crawlingService.checkIfUserSolveProblem(savedUser, savedAssignmentProblem);

            booleanCompletableFuture.exceptionally(
                    throwable -> {
                        throw new AsyncException("비동기 멀티스레딩 중 예외가 발생하였습니다.");
                    }
            );

            result.add(booleanCompletableFuture);
        }

        List<Boolean> booleanList = CompletableFuture.allOf(result.toArray(new CompletableFuture[0]))
                .thenApply(Void -> result.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        stopWatch.stop();
        log.info("==============time: {}================", stopWatch.getTotalTimeSeconds());

        for (Boolean b : booleanList) {
            log.info("problem: {}, solved: {}", savedAssignmentProblem.getProblem().getPid(), b);
        }
    }

    @Test
    void checkIfUserSolveProblem100timesWithSpringAsync() throws ExecutionException, InterruptedException { // @Async 적용 100회 호출 약 2초
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

        List<CompletableFuture<Boolean>> result = new ArrayList<>();
        for(int i = 0; i <100; i++) {
            CompletableFuture<Boolean> booleanCompletableFuture = crawlingService.checkIfUserSolveProblem(savedUser, savedAssignmentProblem);

            booleanCompletableFuture.exceptionally(
                    throwable -> {
                        throw new AsyncException("비동기 멀티스레딩 중 예외가 발생하였습니다.");
                    }
            );

            result.add(booleanCompletableFuture);
        }

        List<Boolean> booleanList = CompletableFuture.allOf(result.toArray(new CompletableFuture[0]))
                .thenApply(Void -> result.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        stopWatch.stop();
        log.info("==============time: {}================", stopWatch.getTotalTimeSeconds());

        for (Boolean b : booleanList) {
            log.info("problem: {}, solved: {}", savedAssignmentProblem.getProblem().getPid(), b);
        }
    }
}