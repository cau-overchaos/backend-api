package algogather.api.exception.programminglanguage;

public class ProgrammingLanguageNotFoundException extends RuntimeException{
    public ProgrammingLanguageNotFoundException() {
        super("해당 프로그래밍 언어가 존재하지 않습니다.");
    }
}
