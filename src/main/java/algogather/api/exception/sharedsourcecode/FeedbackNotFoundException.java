package algogather.api.exception.sharedsourcecode;

public class FeedbackNotFoundException extends RuntimeException{
    public FeedbackNotFoundException() {
        super("해당 피드백을 찾을 수 없습니다.");
    }
}
