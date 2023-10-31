package algogather.api.exception.studyroom;

public class ChangeMyStudyRoomAuthorityException extends RuntimeException{
    public ChangeMyStudyRoomAuthorityException() {
        super("자신의 권한을 변경할 수 없습니다.");
    }
}
