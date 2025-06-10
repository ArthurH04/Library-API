package io.github.library.libraryapi.controller.common;

import io.github.library.libraryapi.controller.DTO.ResponseError;
import io.github.library.libraryapi.controller.DTO.CustomFieldError;
import io.github.library.libraryapi.exceptions.DuplicateEntryException;
import io.github.library.libraryapi.exceptions.InvalidFieldException;
import io.github.library.libraryapi.exceptions.OperationNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<CustomFieldError> errorsList = fieldErrors.stream().map(fe -> new CustomFieldError(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList());
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", errorsList);
    }

    @ExceptionHandler(DuplicateEntryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleDuplicateEntryException(DuplicateEntryException e) {
        return ResponseError.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleOperationNotAllowedException(OperationNotAllowedException e) {
        return ResponseError.defaultResponse(e.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleUnaddressedError(InvalidFieldException e) {
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", List.of(new CustomFieldError(e.getField(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleOperationNotAllowedException(RuntimeException e) {
        return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error has occurred. Please contact the administration", List.of());
    }
}
