package algogather.api.exception.sharedsourcecode;

public class SharedSourceCodeNotFoundException extends RuntimeException{
    public SharedSourceCodeNotFoundException() {
        super("해당 공유 소스코드가 존재하지 않습니다.");
    }
}
