package algogather.api.exception.compile;

public class CompilerException extends RuntimeException {

    public CompilerException() {
        super("컴파일러 서버와 통신과정에서 오류가 발생하였습니다.");
    }
}
