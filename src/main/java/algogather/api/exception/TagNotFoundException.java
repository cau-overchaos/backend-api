package algogather.api.exception;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException() {
        super("해당 태그를 찾을 수 없습니다!");
    }
}
