package io.github.library.libraryapi.service;

import io.github.library.libraryapi.controller.DTO.BookRegisterDTO;
import io.github.library.libraryapi.mappers.BookMapper;
import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.repository.AuthorRepository;
import io.github.library.libraryapi.repository.BookRepository;
import io.github.library.libraryapi.validator.BookValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    public BookService(BookRepository bookRepository, BookValidator bookValidator) {
        this.bookRepository = bookRepository;
        this.bookValidator = bookValidator;
    }

    public Book save(Book book) {
        bookValidator.validate(book);
        return bookRepository.save(book);
    }

    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
