package io.github.library.libraryapi.controller.common;

import io.github.library.libraryapi.controller.DTO.ResponseError;
import io.github.library.libraryapi.controller.DTO.CustomFieldError;
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
}
