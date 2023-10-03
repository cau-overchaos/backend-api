package algogather.api.exception;

public class ProblemNotFoundException extends RuntimeException{
    public ProblemNotFoundException() {
        super("해당 문제를 찾을 수 없습니다.");
    }
}
