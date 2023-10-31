package algogather.api.exception.studyroom;

public class NotStudyRoomManagerException extends RuntimeException{
    public NotStudyRoomManagerException() {
        super("해당 스터디룸의 관리자가 아닙니다!");
    }
}
