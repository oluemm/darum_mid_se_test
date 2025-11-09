package test.darum.empmgmtsys;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import test.darum.empmgmtsys.common.exceptions.NotFoundException;
import test.darum.empmgmtsys.common.exceptions.ResourceConflictException;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
  }

  @ExceptionHandler(ResourceConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Map<String, String>> handleResourceConflictException(ResourceConflictException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
  }
}
