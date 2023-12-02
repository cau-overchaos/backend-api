package algogather.api.exception.notification;

public class NotNotificationReaderException extends RuntimeException{
    public NotNotificationReaderException() {
        super("해당 알림을 읽을 권한이 없습니다.");
    }
}
