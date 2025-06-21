package io.github.library.libraryapi.service;

import io.github.library.libraryapi.controller.DTO.BookRegisterDTO;
import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.model.BookGenre;
import io.github.library.libraryapi.model.User;
import io.github.library.libraryapi.repository.AuthorRepository;
import io.github.library.libraryapi.repository.BookRepository;
import io.github.library.libraryapi.security.SecurityService;
import io.github.library.libraryapi.validator.BookValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static io.github.library.libraryapi.repository.specs.BookSpecs.*;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;
    private final AuthorRepository authorRepository;
    private final SecurityService securityService;

    public BookService(BookRepository bookRepository, BookValidator bookValidator, AuthorRepository authorRepository, SecurityService securityService) {
        this.bookRepository = bookRepository;
        this.bookValidator = bookValidator;
        this.authorRepository = authorRepository;
        this.securityService = securityService;
    }

    public Book save(Book book) {
        bookValidator.validate(book);
        User user = securityService.getLoggedUser();
        book.setUser(user);
        return bookRepository.save(book);
    }

    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public Page<Book> searchByExample(String isbn, String title, String authorName, BookGenre genre, Integer publicationDate, Integer page, Integer size) {

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

        Pageable pageRequest = PageRequest.of(page, size);

        return bookRepository.findAll(specs, pageRequest);
    }

    public Book updateBook(UUID id, BookRegisterDTO bookRegisterDTO) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isEmpty()) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }

        Author author = authorRepository.findById(bookRegisterDTO.author_id())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + bookRegisterDTO.author_id()));

        Book book = bookOptional.get();
        book.setPublicationDate(bookRegisterDTO.publicationDate());
        book.setIsbn(bookRegisterDTO.isbn());
        book.setPrice(bookRegisterDTO.price());
        book.setGenre(bookRegisterDTO.genre());
        book.setTitle(bookRegisterDTO.title());
        book.setAuthor(author);
        bookValidator.validate(book);
        return bookRepository.save(book);}
}
