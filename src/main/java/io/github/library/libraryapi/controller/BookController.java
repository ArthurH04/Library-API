package io.github.library.libraryapi.controller;

import io.github.library.libraryapi.controller.DTO.BookRegisterDTO;
import io.github.library.libraryapi.controller.DTO.ResponseError;
import io.github.library.libraryapi.exceptions.DuplicateEntryException;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid BookRegisterDTO bookDTO) {
        try {

            Book bookEntity = bookService.save(bookDTO);
            var location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(bookEntity.getId()).toUri();

            return ResponseEntity.created(location).build();

        } catch (DuplicateEntryException e) {
            var dtoError = ResponseError.conflict(e.getMessage());
            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }
}
