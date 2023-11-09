package algogather.api.exception.sharedsourcecode;

public class AlreadyDeletedFeedback extends RuntimeException{
    public AlreadyDeletedFeedback() {
        super("이미 삭제된 피드백입니다.");
    }
}
