package algogather.api.exception.sharedsourcecode;

public class NotSameLineNumberException extends RuntimeException{
    public NotSameLineNumberException() {
        super("부모 피드백이 요청 피드백과 같은 소스코드 라인에 속하지 않습니다!");
    }
}
