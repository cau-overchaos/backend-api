package algogather.api.exception.sharedsourcecode;

public class NotFeedbackWriterException extends RuntimeException{
    public NotFeedbackWriterException() {
        super("피드백 작성자가 아닙니다!");
    }
}
