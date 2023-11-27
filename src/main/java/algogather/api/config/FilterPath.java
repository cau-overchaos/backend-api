package algogather.api.config;

public class FilterPath {
    public static final String[] deploymentWhiteListPOST = {"/signup", "/login", "/logout"};
    public static final String[] deploymentWhiteListGET = {"/studyrooms", "/problems", "/recruits", "/ws/draw"};
    public static final String[] developmentWhiteList = {"/h2-console/**"};

    /**
     * localhost:8080/swagger-ui.html로 API 명세서 접근 가능
     */
    public static final String[] swaggerPath = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};
}
