package algogather.api.exception.handler;

import algogather.api.dto.api.ApiResponse;
import algogather.api.exception.DifficultyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ProblemExceptionHandler {
    @ExceptionHandler(DifficultyNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleDifficultyNotFoundException(DifficultyNotFoundException exception) {
        log.debug("DifficultyNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.createError(exception.getMessage()));
    }
}
