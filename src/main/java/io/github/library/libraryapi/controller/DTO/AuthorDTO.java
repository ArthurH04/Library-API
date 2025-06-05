package io.github.library.libraryapi.controller.DTO;

import io.github.library.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(UUID id,
                        @NotBlank(message = "Required field")
                        @Size(min = 2, max = 100, message = "The field must be between {min} and {max} characters long")
                        String name,

                        @NotNull(message = "Required field")
                        @Past(message = "It can't be a future date")
                        LocalDate birthDate,

                        @NotBlank(message = "Required field")
                        @Size(min = 2, max = 100, message = "The field must be between {min} and {max} characters long")
                        String nationality) {

    @Override
    public String toString() {
        return "\nName: " + name + "\nBirth date: " + birthDate + "\nNationality: " + nationality;
    }

}
