package algogather.api.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("해당 유저 아이디가 존재하지 않습니다.");
    }
}
