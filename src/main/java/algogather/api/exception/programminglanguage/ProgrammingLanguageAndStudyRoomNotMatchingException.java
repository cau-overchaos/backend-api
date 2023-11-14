package algogather.api.exception.programminglanguage;

public class ProgrammingLanguageAndStudyRoomNotMatchingException extends RuntimeException{
    public ProgrammingLanguageAndStudyRoomNotMatchingException() {
        super("해당 스터디방의 사용언어가 아닙니다.");
    }
}
