package algogather.api.exception.studyroom;

public class DeleteMeFromStudyRoomException extends RuntimeException{
    public DeleteMeFromStudyRoomException() {
        super("자기 자신을 스터디룸에서 삭제할 수 없습니다.");
    }
}
