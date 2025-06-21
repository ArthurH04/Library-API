package io.github.library.libraryapi.controller;

import io.github.library.libraryapi.controller.DTO.BookRegisterDTO;
import io.github.library.libraryapi.controller.DTO.BookSearchResultDTO;
import io.github.library.libraryapi.mappers.BookMapper;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.model.BookGenre;
import io.github.library.libraryapi.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> save(@RequestBody @Valid BookRegisterDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        bookService.save(book);
        var location = getLocation(book.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookSearchResultDTO> getDetails(@PathVariable("id") String id) {
        UUID bookId = UUID.fromString(id);

        return bookService.findById(bookId).map(b -> {
            var dto = bookMapper.toDto(b);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<BookSearchResultDTO>> searchBooks(
            @RequestParam(value = "isbn" , required = false) String isbn,
            @RequestParam(value = "title" , required = false) String title,
            @RequestParam(value = "authorName" , required = false) String authorName,
            @RequestParam(value = "genre" , required = false) BookGenre genre,
            @RequestParam(value = "publicationDate" , required = false) Integer publicationDate,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size)
    {
        try {
            Page<Book> result = bookService.searchByExample(isbn, title, authorName, genre, publicationDate, page, size);
            Page<BookSearchResultDTO> map = result.map(bookMapper::toDto);
            return ResponseEntity.ok(map);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateBook(@PathVariable("id") String id, @RequestBody @Valid BookRegisterDTO bookRegisterDTO) {
        try{
            UUID bookId = UUID.fromString(id);
            bookService.updateBook(bookId, bookRegisterDTO);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
