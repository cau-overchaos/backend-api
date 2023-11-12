package algogather.api.exception.recruit;

public class RecruitPostNotFoundException extends RuntimeException{
    public RecruitPostNotFoundException() {
        super("해당 스터디방 모집글이 존재하지 않습니다.");
    }
}
