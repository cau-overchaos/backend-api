package algogather.api.config.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CorsConst {
        @Value("${clientURL}")
        public static String CLIENT_URL; // CORS 설정시 사용되는 프론트엔드 서버 주소
}
