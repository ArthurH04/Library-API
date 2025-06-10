package io.github.library.libraryapi.service;

import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.model.BookGenre;
import io.github.library.libraryapi.repository.BookRepository;
import io.github.library.libraryapi.repository.specs.BookSpecs;
import io.github.library.libraryapi.validator.BookValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.library.libraryapi.repository.specs.BookSpecs.*;

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

    public List<Book> searchByExample(String isbn, String title, String authorName, BookGenre genre, Integer publicationDate) {

        // SELECT * FROM book WHERE 0 = 0
        Specification<Book> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (isbn != null && !isbn.isEmpty()) {
            specs = specs.and(isbnEqual(isbn));
        }
        if (title != null && !title.isEmpty()) {
            specs = specs.and(titleLike(title));
        }
        if (authorName != null && !authorName.isEmpty()) {
            specs = specs.and(authorNameLike(authorName));
        }

        if (genre != null && !genre.name().isEmpty()) {
            specs = specs.and(genreEqual(genre));
        }

        if (publicationDate != null && publicationDate > 0) {
            specs = specs.and(publicationDate(publicationDate));
        }
        return bookRepository.findAll(specs);
    }
}
