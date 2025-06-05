package io.github.library.libraryapi.controller.DTO;

import io.github.library.libraryapi.model.BookGenre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookSearchResultDTO(
        UUID id,
        String title,
        String isbn,
        LocalDate publicationDate,
        BookGenre genre,
        BigDecimal price,
        AuthorDTO authorDTO) {
}
