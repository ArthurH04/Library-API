package io.github.library.libraryapi.controller;

import io.github.library.libraryapi.controller.DTO.BookRegisterDTO;
import io.github.library.libraryapi.controller.DTO.BookSearchResultDTO;
import io.github.library.libraryapi.controller.DTO.ResponseError;
import io.github.library.libraryapi.exceptions.DuplicateEntryException;
import io.github.library.libraryapi.mappers.BookMapper;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.model.BookGenre;
import io.github.library.libraryapi.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("books")
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid BookRegisterDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        bookService.save(book);
        var location = getLocation(book.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<BookSearchResultDTO> getDetails(@PathVariable("id") String id) {
        UUID bookId = UUID.fromString(id);

        return bookService.findById(bookId).map(b -> {
            var dto = bookMapper.toDto(b);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        UUID bookId = UUID.fromString(id);
        var bookOptional = bookService.findById(bookId);
        if (bookOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookService.delete(bookOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BookSearchResultDTO>> searchBooks(
            @RequestParam(value = "isbn" , required = false) String isbn,
            @RequestParam(value = "title" , required = false) String title,
            @RequestParam(value = "authorName" , required = false) String authorName,
            @RequestParam(value = "genre" , required = false) BookGenre genre,
            @RequestParam(value = "publicationDate" , required = false) Integer publicationDate) {
        try {
            List<Book> result = bookService.searchByExample(isbn, title, authorName, genre, publicationDate);
            List<BookSearchResultDTO> list = result.stream().map(bookMapper::toDto).collect(Collectors.toList());
            return ResponseEntity.ok(list);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
