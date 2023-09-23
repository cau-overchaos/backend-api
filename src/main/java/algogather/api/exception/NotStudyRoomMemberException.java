package algogather.api.exception;

public class NotStudyRoomMemberException extends RuntimeException{

    public NotStudyRoomMemberException() {
        super("해당 스터디방의 멤버가 아닙니다.");
    }
}
