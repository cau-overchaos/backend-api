package algogather.api.config.crawler;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
@Slf4j
public class BojCrawler {
    public LocalDateTime GetAcceptedDates(String userId, Long problemId, LocalDateTime assignmentStartDate, LocalDateTime assignmentDueDate) throws IOException {
        String urlFormat = "https://acmicpc.net/status?problem_id=%d&user_id=%s&language_id=-1&result_id=4&from_problem=1"; // result_id가 4여야 맞았습니다! 결과의 채점현황만 가져옴.
        String url = String.format(urlFormat, problemId, userId);

        return getAcceptedDatesFrom(url, assignmentStartDate, assignmentDueDate);
    }

    private LocalDateTime getAcceptedDatesFrom(String url, LocalDateTime assignmentStartDate, LocalDateTime assignmentDueDate) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; Algogather-Bot/0.1; +https://github.com/boulce)").get();
        LocalDateTime resultLocalDateTime = null;


        Elements rows = doc.body().select("#status-table tbody tr");
        for (Element i: rows) {
            Element timeElement = i.selectFirst("[data-timestamp]");
            if (timeElement == null)
                throw new NullPointerException();

            long timestamp = Long.parseLong(timeElement.attr("data-timestamp")) * 1000L;
            LocalDateTime solvedLocalDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();

            if((solvedLocalDateTime.isEqual(assignmentStartDate) || solvedLocalDateTime.isAfter(assignmentStartDate))
            && (solvedLocalDateTime.isEqual(assignmentDueDate) || solvedLocalDateTime.isBefore(assignmentDueDate))) { // 푼날짜가 과제 시작날짜보다 같거나 이후이고, 과제 종료날짜보다 같거나 이전이면 푼것으로 판단.
                log.info("!!!!!!{}", solvedLocalDateTime);
                return solvedLocalDateTime;
            }
            else if(solvedLocalDateTime.isBefore(assignmentStartDate)) { // 푼 날짜가 과제 시작 날짜 이전이면 과제 기간동안 과제를 풀지 않은 것이므로 크롤링 종료
                return null;
            } // 푼날짜가 과제 종료날짜 이후일 수가 없다. 왜냐하면 진행중인 과제에 대해서만(현재날짜가 종료날짜 이전인 과제들만) 크롤링을 하기 때문이다.
        }

        Element nextPageLink = doc.selectFirst("a#next_page");
        if (nextPageLink != null) {
            String nextUrl = nextPageLink.absUrl("href");

            resultLocalDateTime = getAcceptedDatesFrom(nextUrl, assignmentStartDate, assignmentDueDate);
        }

        return resultLocalDateTime;
    }
}
