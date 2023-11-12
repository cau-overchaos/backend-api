package algogather.api.exception.handler;

import algogather.api.dto.api.ApiResponse;
import algogather.api.exception.programminglanguage.ProgrammingLanguageAndStudyRoomNotMatchingException;
import algogather.api.exception.programminglanguage.ProgrammingLanguageNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ProgrammingLanugageExceptionHandler {
    @ExceptionHandler(ProgrammingLanguageNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleProgrammingLanguageNotFoundException(ProgrammingLanguageNotFoundException exception) {
        log.debug("ProgrammingLanguageNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(ProgrammingLanguageAndStudyRoomNotMatchingException.class)
    public ResponseEntity<ApiResponse<?>> handleProgrammingLanguageAndStudyRoomNotMatchingException(ProgrammingLanguageAndStudyRoomNotMatchingException exception) {
        log.debug("ProgrammingLanguageAndStudyRoomNotMatchingException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }
}
