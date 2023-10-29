package algogather.api.exception.studyroom;

public class AlreadyExistingStudyRoomMemberException extends RuntimeException{
    public AlreadyExistingStudyRoomMemberException() {
        super("스터디방에 이미 존재하는 스터디 멤버입니다.");
    }
}
