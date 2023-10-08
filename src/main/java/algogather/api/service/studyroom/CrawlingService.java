package algogather.api.service.studyroom;

import algogather.api.config.crawler.BojCrawler;
import algogather.api.domain.assignment.AssignmentProblem;
import algogather.api.domain.assignment.AssignmentSolveRepository;
import algogather.api.domain.problem.ProblemProvider;
import algogather.api.domain.user.User;
import algogather.api.exception.NotSupportedProviderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final AssignmentSolveRepository assignmentSolveRepository;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<LocalDateTime> checkIfUserSolveProblemOnBAEKJOON(User user, AssignmentProblem assignmentProblem) {
        if(assignmentProblem.getProblem().getProvider() == ProblemProvider.BAEKJOON) {
            BojCrawler crawler = new BojCrawler();

            try {
                //TODO 과제 기간 정보도 보내기
                LocalDateTime localDateTime = crawler.GetAcceptedDates(user.getJudgeAccount(), assignmentProblem.getProblem().getPid(), assignmentProblem.getStartDate(), assignmentProblem.getDueDate());

                return CompletableFuture.completedFuture(localDateTime); // 푼 날짜가 없으면 null을 보낸다.


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            throw new NotSupportedProviderException();
        }
    }
}
