package algogather.api.exception.sharedsourcecode;

public class NotSameSourceCodeException extends RuntimeException{
    public NotSameSourceCodeException() {
        super("부모 피드백이 요청 피드백과 같은 소스코드에 속하지 않습니다!");
    }
}
