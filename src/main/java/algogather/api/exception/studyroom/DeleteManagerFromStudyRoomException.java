package algogather.api.exception.studyroom;

public class DeleteManagerFromStudyRoomException extends RuntimeException{
    public DeleteManagerFromStudyRoomException() {
        super("관리자를 스터디룸에서 삭제할 수 없습니다.");
    }
}
