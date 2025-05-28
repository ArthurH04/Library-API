package io.github.library.libraryapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleUUIDException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorDTO("Enter a valid UUID in the ‘id’ parameter."));
    }
}
