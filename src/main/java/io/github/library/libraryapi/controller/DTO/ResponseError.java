package io.github.library.libraryapi.controller.DTO;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

public record ResponseError(int status, String message, List<CustomFieldError> errors) {

    public static ResponseError defaultResponse(String message) {
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ResponseError conflict(String message){
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }
}
