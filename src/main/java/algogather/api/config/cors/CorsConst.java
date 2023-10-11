package algogather.api.config.cors;

import org.springframework.stereotype.Component;

@Component
public class CorsConst {
        public final static String[] frontendServer = {"http://localhost:3000", "https://algogather.com"}; // CORS 설정시 사용되는 프론트엔드 서버 주소
}
