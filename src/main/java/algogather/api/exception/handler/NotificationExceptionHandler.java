package algogather.api.exception.handler;

import algogather.api.dto.api.ApiResponse;
import algogather.api.exception.notification.NotNotificationReaderException;
import algogather.api.exception.notification.NotificationNotFoundException;
import algogather.api.exception.problem.DifficultyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class NotificationExceptionHandler {
    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotificationNotFoundException(NotificationNotFoundException exception) {
        log.debug("NotificationNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(NotNotificationReaderException.class)
    public ResponseEntity<ApiResponse<?>> handleNotNotificationReaderException(NotNotificationReaderException exception) {
        log.debug("NotNotificationReaderException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.createError(exception.getMessage()));
    }
}
