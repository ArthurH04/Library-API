package io.github.library.libraryapi.controller.DTO;

import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.model.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookRegisterDTO(
        @NotBlank(message = "Required field")
        String title,

        @NotBlank(message = "Required field")
        @ISBN
        String isbn,

        @NotNull(message = "Required field")
        @Past(message = "It can't be a future date")
        LocalDate publicationDate,

        BookGenre genre,
        BigDecimal price,

        @NotNull(message = "Required field")
        UUID author_id) {
}
