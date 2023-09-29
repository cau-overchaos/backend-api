package algogather.api.config.init;

import algogather.api.domain.problem.Difficulty;
import algogather.api.domain.problem.DifficultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("dev")
@RequiredArgsConstructor
@Component
public class initData {


    public  final DifficultyRepository difficultyRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initDifficulty() {
        /**
         * 백준 난이도 데이터 초기화
         */
        difficultyRepository.save(Difficulty.builder().name("Unrated").level(0).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze V").level(1).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze IV").level(2).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze III").level(3).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze II").level(4).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze I").level(5).build());
        difficultyRepository.save(Difficulty.builder().name("Silver V").level(6).build());
        difficultyRepository.save(Difficulty.builder().name("Silver IV").level(7).build());
        difficultyRepository.save(Difficulty.builder().name("Silver III").level(8).build());
        difficultyRepository.save(Difficulty.builder().name("Silver II").level(9).build());
        difficultyRepository.save(Difficulty.builder().name("Silver I").level(10).build());
        difficultyRepository.save(Difficulty.builder().name("Gold V").level(11).build());
        difficultyRepository.save(Difficulty.builder().name("Gold IV").level(12).build());
        difficultyRepository.save(Difficulty.builder().name("Gold III").level(13).build());
        difficultyRepository.save(Difficulty.builder().name("Gold II").level(14).build());
        difficultyRepository.save(Difficulty.builder().name("Gold I").level(15).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum V").level(16).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum IV").level(17).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum III").level(18).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum II").level(19).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum I").level(20).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond V").level(21).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond IV").level(22).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond III").level(23).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond II").level(24).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond I").level(25).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby V").level(26).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby IV").level(27).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby III").level(28).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby II").level(29).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby I").level(30).build());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initProblems() {

    }
}
