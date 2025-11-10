package test.darum.empmgmtsys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import test.darum.empmgmtsys.common.constants.Errors;
import test.darum.empmgmtsys.common.exceptions.NotFoundException;
import test.darum.empmgmtsys.common.exceptions.ResourceConflictException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
    log.error(Errors.NOT_FOUND, ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
  }

  @ExceptionHandler(ResourceConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Map<String, String>> handleResourceConflictException(ResourceConflictException ex) {
    log.error(Errors.CONFLICT, ex);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {
    var errors = new HashMap<String, String>();
    BindingResult bindingResult = exception.getBindingResult();
    bindingResult.getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });
    log.warn("Validation Errors", errors);
    return ResponseEntity.badRequest().body(errors);
  }
}
