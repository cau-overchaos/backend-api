package algogather.api.exception.handler;

import algogather.api.dto.api.ApiResponse;
import algogather.api.exception.problem.DifficultyNotFoundException;
import algogather.api.exception.problem.ProblemNotFoundException;
import algogather.api.exception.problem.TagNotFoundException;
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleTagNotFoundException(TagNotFoundException exception) {
        log.debug("TagNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(ProblemNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleProblemNotFoundException(ProblemNotFoundException exception) {
        log.debug("ProblemNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }
}
