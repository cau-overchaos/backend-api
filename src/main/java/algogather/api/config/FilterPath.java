package algogather.api.config;

public class FilterPath {
    public static final String[] whiteList = {"/h2-console/**", "/signup/**", "/login", "/logout", "/swagger-ui/**", "/docs/**"};

    /**
     * localhost:8080/swagger-ui.html로 API 명세서 접근 가능
     */
    public static final String[] swaggerPath = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};
}
