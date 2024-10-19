package algogather.api.config.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class InitConst {
    public final static String SOLVED_AC_URL = "https://solved.ac";

    public static String COMPILER_SERVER_URL; // 컴파일러 서버 주소

    @Value("${compilerURL}")
    public void setKey(String value) {
        COMPILER_SERVER_URL = value;
    }
}
