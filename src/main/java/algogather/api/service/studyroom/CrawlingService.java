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
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final AssignmentSolveRepository assignmentSolveRepository;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Boolean> checkIfUserSolveProblem(User user, AssignmentProblem assignmentProblem) {
        if(assignmentProblem.getProblem().getProvider() == ProblemProvider.BAEKJOON) {
            //AssignmentSolve에서 우선 문제를 푼 기록이 있는지 확인, 있으면 true 반환
            if(assignmentSolveRepository.existsByUserIdAndAssignmentProblemId(user.getId(), assignmentProblem.getId())) {
                return CompletableFuture.completedFuture(true);
            }
            else {  // 없으면 크롤링해서 정보 가져옴 기간안에 풀었으면 true 반환
                BojCrawler crawler = new BojCrawler();

                try {
                    List<Date> result =  crawler.GetAcceptedDates(user.getJudgeAccount(), assignmentProblem.getProblem().getPid());
                    log.debug("{} results", result.size());
                    for (Date i: result) {
//                        System.out.printf("Accepted at %s\n", i.toString());
                    }

                    if(!result.isEmpty()) { // 있으면 true 반환 //TODO 기간 안에 풀었는지 판단 로직 만들기
                        return CompletableFuture.completedFuture(true);
                    }
                    else { // 없으면 false 반환
                        return CompletableFuture.completedFuture(false);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            throw new NotSupportedProviderException();
        }
    }
}
