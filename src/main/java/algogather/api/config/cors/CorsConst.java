package algogather.api.config.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CorsConst {
        public static String CLIENT_URL; // CORS 설정시 사용되는 프론트엔드 서버 주소

        @Value("${clientURL}")
        public void setKey(String value) {
                CLIENT_URL = value;
        }
}
