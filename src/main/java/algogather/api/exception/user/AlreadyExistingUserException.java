package algogather.api.exception.user;

public class AlreadyExistingUserException extends RuntimeException{
    public AlreadyExistingUserException() {
        super("이미 존재하는 아이디입니다.");
    }
}