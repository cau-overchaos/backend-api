package algogather.api.config.init;

import algogather.api.domain.problem.Difficulty;
import algogather.api.domain.problem.DifficultyRepository;
import algogather.api.domain.problem.Tag;
import algogather.api.domain.problem.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static algogather.api.domain.problem.ProblemProvider.*;

@Profile("dev")
@RequiredArgsConstructor
@Component
@Slf4j
public class initData {
    public final DifficultyRepository difficultyRepository;
    public final TagRepository tagRepository;

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
    public void initTags() throws ParseException {
        /**
         * solved.ac API를 호출해서 태그 정보를 받는다.
         */

        String url = "https://solved.ac/";

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("api/v3/tag/list")
                .encode()
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(req, String.class);


        /**
         * JSON 문자열 정보를 객체로 변환한다.
         * 태그의 번호와 한국어 이름을 Tag DB에 저장한다.
         */
        JSONParser jsonParser = new JSONParser();
        JSONObject parsedObject = (JSONObject) jsonParser.parse(response.getBody());

        JSONArray items = (JSONArray) parsedObject.get("items");

        for(int i = 0; i < items.size(); i++) {
            JSONObject item = (JSONObject) items.get(i);
            JSONArray displayNames = (JSONArray) item.get("displayNames");

            String tagName = null;
            Long bojTagId = (Long) item.get("bojTagId");

            log.info("{}", item.get("bojTagId"));

            for(int j = 0; j < displayNames.size(); j++) {
                JSONObject displayName = (JSONObject) displayNames.get(j);

                String language = (String) displayName.get("language");
                if(language.equals("ko")) { // 한국어 태그 이름만 태그에 저장한다.
                    tagName = (String) displayName.get("name");
                    break;
                }
            }

            Tag tag = Tag.builder()
                    .name(tagName)
                    .idByProvider(bojTagId)
                    .provider(BAEKJOON)
                    .build();

            tagRepository.save(tag);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initProblems() {

    }
}
