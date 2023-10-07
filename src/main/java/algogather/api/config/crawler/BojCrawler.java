package algogather.api.config.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class BojCrawler {
    public List<Date> GetAcceptedDates(String userId, int problemId) throws IOException {
        String urlFormat = "https://acmicpc.net/status?problem_id=%d&user_id=%s&language_id=-1&result_id=-1&from_problem=1";
        String url = String.format(urlFormat, problemId, userId);

        return getAcceptedDatesFrom(url);
    }

    private List<Date> getAcceptedDatesFrom(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; Algogather-Bot/0.1; +https://github.com/cau-overchaos)").get();
        ArrayList<Date> result = new ArrayList<Date>();

        Elements rows = doc.body().select("#status-table tbody tr");
        for (Element i: rows) {
            boolean ac = i.selectFirst(".result-ac") != null;
            if (ac) {
                Element timeElement = i.selectFirst("[data-timestamp]");
                if (timeElement == null)
                    throw new NullPointerException();

                long timestamp = Long.parseLong(timeElement.attr("data-timestamp")) * 1000L;
                Date date = new Date(timestamp);

                result.add(date);
            }
        }

        Element nextPageLink = doc.selectFirst("a#next_page");
        if (nextPageLink != null) {
            String nextUrl = nextPageLink.absUrl("href");
            result.addAll(getAcceptedDatesFrom(nextUrl));
        }

        return result;
    }
}
