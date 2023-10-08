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


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@SpringBootTest
@Transactional
@Slf4j
// judgeAcoount: boulce, pid: 20183에 대하여 test
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
                .pid(20183L)
                .provider(ProblemProvider.BAEKJOON)
                .build();

        Problem savedProblem = problemRepository.save(problem);

        AssignmentProblem assignmentProblem = AssignmentProblem.builder()
                .problem(savedProblem)
                .startDate(LocalDateTime.parse("2023-01-17T15:16:48"))
                .dueDate(LocalDateTime.now())
                .build();

        AssignmentProblem savedAssignmentProblem = assignmentProblemRepository.save(assignmentProblem);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<CompletableFuture<LocalDateTime>> result = new ArrayList<>();

        CompletableFuture<LocalDateTime> localDateTimeCompletableFuture = crawlingService.checkIfUserSolveProblemOnBAEKJOON(savedUser, savedAssignmentProblem);

        localDateTimeCompletableFuture.exceptionally(
                throwable -> {
                    throw new AsyncException("비동기 멀티스레딩 중 예외가 발생하였습니다.");
                }
        );

        result.add(localDateTimeCompletableFuture);

        List<LocalDateTime> localDateTimes = CompletableFuture.allOf(result.toArray(new CompletableFuture[0]))
                .thenApply(Void -> result.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        stopWatch.stop();
        log.info("==============time: {}================", stopWatch.getTotalTimeSeconds());

        for (LocalDateTime localDateTime : localDateTimes) {
            log.info("problem: {}, solved: {}", savedAssignmentProblem.getProblem().getPid(), localDateTime);
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
                .pid(20183L)
                .provider(ProblemProvider.BAEKJOON)
                .build();

        Problem savedProblem = problemRepository.save(problem);

        AssignmentProblem assignmentProblem = AssignmentProblem.builder()
                .problem(savedProblem)
                .startDate(LocalDateTime.parse("2023-01-17T15:16:48"))
                .dueDate(LocalDateTime.now())
                .build();

        AssignmentProblem savedAssignmentProblem = assignmentProblemRepository.save(assignmentProblem);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<CompletableFuture<LocalDateTime>> result = new ArrayList<>();
        for(int i = 0; i <50; i++) {
            CompletableFuture<LocalDateTime> localDateTimeCompletableFuture = crawlingService.checkIfUserSolveProblemOnBAEKJOON(savedUser, savedAssignmentProblem);

            localDateTimeCompletableFuture.exceptionally(
                    throwable -> {
                        throw new AsyncException("비동기 멀티스레딩 중 예외가 발생하였습니다.");
                    }
            );

            result.add(localDateTimeCompletableFuture);
        }

        List<LocalDateTime> localDateTimes = CompletableFuture.allOf(result.toArray(new CompletableFuture[0]))
                .thenApply(Void -> result.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        stopWatch.stop();
        log.info("==============time: {}================", stopWatch.getTotalTimeSeconds());

        for (LocalDateTime localDateTime : localDateTimes) {
            log.info("problem: {}, solved: {}", savedAssignmentProblem.getProblem().getPid(), localDateTime);
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
                .pid(20183L)
                .provider(ProblemProvider.BAEKJOON)
                .build();

        Problem savedProblem = problemRepository.save(problem);

        AssignmentProblem assignmentProblem = AssignmentProblem.builder()
                .problem(savedProblem)
                .startDate(LocalDateTime.parse("2023-01-17T15:16:48"))
                .dueDate(LocalDateTime.now())
                .build();

        AssignmentProblem savedAssignmentProblem = assignmentProblemRepository.save(assignmentProblem);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<CompletableFuture<LocalDateTime>> result = new ArrayList<>();
        for(int i = 0; i <50; i++) {
            CompletableFuture<LocalDateTime> localDateTimeCompletableFuture = crawlingService.checkIfUserSolveProblemOnBAEKJOON(savedUser, savedAssignmentProblem);

            localDateTimeCompletableFuture.exceptionally(
                    throwable -> {
                        throw new AsyncException("비동기 멀티스레딩 중 예외가 발생하였습니다.");
                    }
            );

            result.add(localDateTimeCompletableFuture);
        }

        List<LocalDateTime> localDateTimes = CompletableFuture.allOf(result.toArray(new CompletableFuture[0]))
                .thenApply(Void -> result.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        stopWatch.stop();
        log.info("==============time: {}================", stopWatch.getTotalTimeSeconds());

        for (LocalDateTime localDateTime : localDateTimes) {
            log.info("problem: {}, solved: {}", savedAssignmentProblem.getProblem().getPid(), localDateTime);
        }
    }
}