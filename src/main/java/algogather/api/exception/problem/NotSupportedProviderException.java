package algogather.api.exception.problem;

public class NotSupportedProviderException extends RuntimeException{
    public NotSupportedProviderException() {
        super("지원하지 않는 문제 출처입니다.");
    }
}
