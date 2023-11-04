package algogather.api.exception.handler;

import algogather.api.dto.api.ApiResponse;
import algogather.api.exception.sharedsourcecode.SharedSourceCodeAndStudyRoomNotMatchingException;
import algogather.api.exception.sharedsourcecode.SharedSourceCodeNotFoundException;
import algogather.api.exception.studyroom.AlreadyExistingStudyRoomMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SharedSourceCodeExceptionHandler {
    @ExceptionHandler(SharedSourceCodeNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleSharedSourceCodeNotFoundException(SharedSourceCodeNotFoundException exception) {
        log.debug("SharedSourceCodeNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(SharedSourceCodeAndStudyRoomNotMatchingException.class)
    public ResponseEntity<ApiResponse<?>> handleSharedSourceCodeAndStudyRoomNotMatchingException(SharedSourceCodeAndStudyRoomNotMatchingException exception) {
        log.debug("SharedSourceCodeAndStudyRoomNotMatchingException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }
}
