package algogather.api.exception;

public class DifficultyNotFoundException extends RuntimeException{
    public DifficultyNotFoundException() {
        super("해당 난이도를 찾을 수 없습니다!");
    }
}
