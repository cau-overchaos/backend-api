package algogather.api.exception.studyroom;

public class ChangeMyStudyRoomException extends RuntimeException{
    public ChangeMyStudyRoomException() {
        super("자신의 권한을 변경할 수 없습니다.");
    }
}
