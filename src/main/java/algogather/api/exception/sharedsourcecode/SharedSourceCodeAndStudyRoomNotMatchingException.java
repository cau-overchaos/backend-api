package algogather.api.exception.sharedsourcecode;

public class SharedSourceCodeAndStudyRoomNotMatchingException extends RuntimeException{
    public SharedSourceCodeAndStudyRoomNotMatchingException() {
        super("해당 스터디방의 소스코드가 아닙니다.");
    }
}
