package algogather.api.exception.notification;

public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException() {
        super("해당 알림을 찾을 수 없습니다!");
    }
}
