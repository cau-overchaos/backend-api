package algogather.api.config.init;

import algogather.api.domain.problem.Difficulty;
import algogather.api.domain.problem.DifficultyRepository;
import algogather.api.domain.problem.ProblemProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static algogather.api.domain.problem.ProblemProvider.*;

@Profile("dev")
@RequiredArgsConstructor
@Component
public class initData {


    public  final DifficultyRepository difficultyRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initDifficulties() {
        /**
         * 백준 난이도 데이터 초기화
         */
        difficultyRepository.save(Difficulty.builder().name("Unrated").level(0).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze V").level(1).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze IV").level(2).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze III").level(3).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze II").level(4).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze I").level(5).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Silver V").level(6).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Silver IV").level(7).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Silver III").level(8).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Silver II").level(9).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Silver I").level(10).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Gold V").level(11).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Gold IV").level(12).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Gold III").level(13).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Gold II").level(14).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Gold I").level(15).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum V").level(16).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum IV").level(17).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum III").level(18).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum II").level(19).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum I").level(20).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond V").level(21).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond IV").level(22).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond III").level(23).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond II").level(24).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond I").level(25).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby V").level(26).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby IV").level(27).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby III").level(28).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby II").level(29).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby I").level(30).provider(BAEKJOON).build());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initTags() {

    }

    @EventListener(ApplicationReadyEvent.class)
    public void initProblems() {

    }
}
