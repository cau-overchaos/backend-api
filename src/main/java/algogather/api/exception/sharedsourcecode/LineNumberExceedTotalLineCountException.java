package algogather.api.exception.sharedsourcecode;

public class LineNumberExceedTotalLineCountException extends RuntimeException{
    public LineNumberExceedTotalLineCountException() {
        super("피드백을 달 코드 라인 번호는 코드의 총 라인 개수보다 크면 안됩니다!");
    }
}
