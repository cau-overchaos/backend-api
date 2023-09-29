package algogather.api.config.init;

import algogather.api.domain.problem.*;
import algogather.api.exception.DifficultyNotFoundException;
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
    private final ProblemRepository problemRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initDifficulties() {
        /**
         * 백준 난이도 데이터 초기화
         */
        difficultyRepository.save(Difficulty.builder().name("Unrated").level(0L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze V").level(1L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze IV").level(2L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze III").level(3L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze II").level(4L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Bronze I").level(5L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Silver V").level(6L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Silver IV").level(7L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Silver III").level(8L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Silver II").level(9L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Silver I").level(10L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Gold V").level(11L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Gold IV").level(12L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Gold III").level(13L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Gold II").level(14L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Gold I").level(15L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum V").level(16L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum IV").level(17L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum III").level(18L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum II").level(19L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Platinum I").level(20L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond V").level(21L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond IV").level(22L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond III").level(23L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond II").level(24L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Diamond I").level(25L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby V").level(26L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby IV").level(27L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby III").level(28L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby II").level(29L).provider(BAEKJOON).build());
        difficultyRepository.save(Difficulty.builder().name("Ruby I").level(30L).provider(BAEKJOON).build());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initTags() throws ParseException {
        /**
         * solved.ac API를 호출해서 태그 정보를 받는다.
         */

        ResponseEntity<String> response = getStringResponseEntity();

        /**
         * JSON 문자열 정보를 객체로 변환한다.
         * 태그의 번호와 한국어 이름을 Tag DB에 저장한다.
         */

        parseAndSaveTag(response);
    }

    private static ResponseEntity<String> getStringResponseEntity() {
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
        return response;
    }

    private void parseAndSaveTag(ResponseEntity<String> response) throws ParseException {
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

    //TODO 문제 응답 받아서 DB에 저장
    @EventListener(ApplicationReadyEvent.class)
    public void initProblems() throws ParseException {
        String url = "https://solved.ac/";
        RestTemplate restTemplate = new RestTemplate();

        for(int page = 1;; page++) {
            /**
             * solved.ac API를 호출해서 페이지별로 문제 정보를 받는다.
             */

            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .path("api/v3/search/problem")
                    .queryParam("query", "")
                    .queryParam("sort", "id")
                    .queryParam("page", page)
                    .encode()
                    .build()
                    .toUri();

            RequestEntity<Void> req = RequestEntity
                    .get(uri)
                    .build();

            ResponseEntity<String> response = restTemplate.exchange(req, String.class);

            /**
             * JSON 문자열 정보를 객체로 변환한다.
             */

            JSONParser jsonParser = new JSONParser();
            JSONObject parsedObject = (JSONObject) jsonParser.parse(response.getBody());

            JSONArray items = (JSONArray) parsedObject.get("items");

            if(items.isEmpty()) { // API 응답의 문제가 빈리스트라면 반복을 종료한다.
                break;
            }

            for(int i = 0; i < items.size(); i++) {
                JSONObject item = (JSONObject) items.get(i);
                JSONArray titles = (JSONArray) item.get("titles");

                String problemTitle = null;
                Long problemId = (Long) item.get("problemId");
                Long problemLevel = (Long) item.get("level");

                for(int j = 0; j < titles.size(); j++) {
                    JSONObject title = (JSONObject) titles.get(j);

                    String language = (String)title.get("language");

                    if(language.equals("ko")) {
                        problemTitle = (String) title.get("title");
                        break;
                    }
                }

                Problem problem = Problem.builder()
                        .pid(problemId)
                        .title(problemTitle)
                        .difficulty(difficultyRepository
                                .findByLevel(problemLevel)
                                .orElseThrow(() -> new DifficultyNotFoundException())
                        )
                        .provider(BAEKJOON)
                        .build();

                problemRepository.save(problem);

                //TODO 태그 연관시키기
            }
        }
    }
}
