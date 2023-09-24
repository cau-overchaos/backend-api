package algogather.api.exception;

public class StudyRoomNotFoundException extends RuntimeException{
    public StudyRoomNotFoundException() {
        super("해당 스터디룸을 찾을 수 없습니다.");
    }
}
