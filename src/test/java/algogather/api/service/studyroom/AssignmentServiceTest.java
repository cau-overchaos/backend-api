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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("유저가 과제 기간 동안에 문제를 정상적으로 풀었을때 테스트")
    void checkIfUserSolveProblem_solved_test() throws ExecutionException, InterruptedException {
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
                .startDate(LocalDateTime.parse("2023-01-17T15:19:01"))
                .dueDate(LocalDateTime.parse("2023-01-17T15:19:03"))
                .build();

        AssignmentProblem savedAssignmentProblem = assignmentProblemRepository.save(assignmentProblem);


        CompletableFuture<LocalDateTime> localDateTimeCompletableFuture = crawlingService.checkIfUserSolveProblemOnBAEKJOON(savedUser, savedAssignmentProblem);

        localDateTimeCompletableFuture.exceptionally(
                throwable -> {
                    throw new AsyncException("비동기 멀티스레딩 중 예외가 발생하였습니다.");
                }
        );

        LocalDateTime join = localDateTimeCompletableFuture.join();

        Assertions.assertThat(join).isNotNull();
        Assertions.assertThat(join).isAfterOrEqualTo("2023-01-17T15:19:01");
        Assertions.assertThat(join).isBeforeOrEqualTo("2023-01-17T15:19:04");
        Assertions.assertThat(join).isAfterOrEqualTo("2023-01-17T15:19:03");

    }

    @Test
    @DisplayName("유저가 과제 기간 동안에 문제를 정상적으로 풀었을때 경계값 테스트")
    void checkIfUserSolveProblem_solved_boundary_test() throws ExecutionException, InterruptedException {
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
                .startDate(LocalDateTime.parse("2023-01-17T15:19:03"))
                .dueDate(LocalDateTime.parse("2023-01-17T15:19:03"))
                .build();

        AssignmentProblem savedAssignmentProblem = assignmentProblemRepository.save(assignmentProblem);


        CompletableFuture<LocalDateTime> localDateTimeCompletableFuture = crawlingService.checkIfUserSolveProblemOnBAEKJOON(savedUser, savedAssignmentProblem);

        localDateTimeCompletableFuture.exceptionally(
                throwable -> {
                    throw new AsyncException("비동기 멀티스레딩 중 예외가 발생하였습니다.");
                }
        );

        LocalDateTime join = localDateTimeCompletableFuture.join();

        Assertions.assertThat(join).isNotNull();
        Assertions.assertThat(join).isAfterOrEqualTo("2023-01-17T15:19:03");
        Assertions.assertThat(join).isBeforeOrEqualTo("2023-01-17T15:19:03");
    }

    @Test
    @DisplayName("유저가 과제 기간 동안에 문제를 풀지 않았을때 테스트")
    void checkIfUserSolveProblem_not_solved_test() throws ExecutionException, InterruptedException {
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
                .startDate(LocalDateTime.parse("2023-01-17T15:19:04"))
                .dueDate(LocalDateTime.parse("2023-01-17T15:19:06"))
                .build();

        AssignmentProblem savedAssignmentProblem = assignmentProblemRepository.save(assignmentProblem);


        CompletableFuture<LocalDateTime> localDateTimeCompletableFuture = crawlingService.checkIfUserSolveProblemOnBAEKJOON(savedUser, savedAssignmentProblem);

        localDateTimeCompletableFuture.exceptionally(
                throwable -> {
                    throw new AsyncException("비동기 멀티스레딩 중 예외가 발생하였습니다.");
                }
        );

        LocalDateTime join = localDateTimeCompletableFuture.join();

        Assertions.assertThat(join).isNull();
    }


    @Test
    @DisplayName("스프링 Async 사용하여 크롤링 1회 시간측정 약 0.4초")
    void checkIfUserSolveProblemOnceWithSpringAsync() throws ExecutionException, InterruptedException {
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
    @DisplayName("스프링 Async 사용하여 크롤링 50회 시간측정 약 1초")
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
    @DisplayName("스프링 Async 사용하여 크롤링 50회 시간측정 약 2초")
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