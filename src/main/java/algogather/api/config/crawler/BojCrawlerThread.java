package algogather.api.config.crawler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Getter
public class BojCrawlerThread implements Runnable{
    private Thread thread;
    private String userId;
    private Long problemId;
    private int ID;
    List<Date> resultAcceptedDates;

    public BojCrawlerThread(String userId, Long problemId, int ID) {
        log.debug("BojCrawler #{} Created", ID);
        this.userId = userId;
        this.problemId = problemId;
        this.ID = ID;

        thread = new Thread(this::run);
        thread.start();
    }

    @Override
    public void run() {
        try {
            resultAcceptedDates = getAcceptedDates();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Date> getAcceptedDates() throws IOException {
        String urlFormat = "https://acmicpc.net/status?problem_id=%d&user_id=%s&language_id=-1&result_id=4&from_problem=1"; //result_id가 4여야 맞았습니다! 결과의 채점현황만 가져옴.
        String url = String.format(urlFormat, problemId, userId);

        return getAcceptedDatesFrom(url);
    }

    private List<Date> getAcceptedDatesFrom(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; Algogather-Bot/0.1; +https://github.com/cau-overchaos)").get();
        ArrayList<Date> result = new ArrayList<Date>();

        Elements rows = doc.body().select("#status-table tbody tr");
        for (Element i: rows) {
            Element timeElement = i.selectFirst("[data-timestamp]");
            if (timeElement == null)
                throw new NullPointerException();

            long timestamp = Long.parseLong(timeElement.attr("data-timestamp")) * 1000L;
            Date date = new Date(timestamp);

            //TODO 마감 날짜보다 오래된 날짜 나오면 중지

            result.add(date);
        }

        Element nextPageLink = doc.selectFirst("a#next_page");
        if (nextPageLink != null) {
            String nextUrl = nextPageLink.absUrl("href");
            result.addAll(getAcceptedDatesFrom(nextUrl));
        }

        return result;
    }

}
