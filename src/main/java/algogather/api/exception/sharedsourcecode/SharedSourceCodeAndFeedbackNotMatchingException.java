package algogather.api.exception.sharedsourcecode;

public class SharedSourceCodeAndFeedbackNotMatchingException extends RuntimeException{
    public SharedSourceCodeAndFeedbackNotMatchingException() {
        super("해당 소스코드의 피드백이 안닙니다.");
    }
}
