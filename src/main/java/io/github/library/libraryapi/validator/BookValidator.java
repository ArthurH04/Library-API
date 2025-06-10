package io.github.library.libraryapi.validator;

import io.github.library.libraryapi.exceptions.DuplicateEntryException;
import io.github.library.libraryapi.exceptions.InvalidFieldException;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class BookValidator {
    private final BookRepository bookRepository;
    private static final int MINIMUM_YEAR_PRICE_VALIDATION = 2020;

    public void validate(Book book) {
        if (existsBookWithIsbn(book)) {
            throw new DuplicateEntryException("Book already registered with ISBN " + book.getIsbn());
        }

        if (isMandatoryPriceNull(book)) {
        throw new InvalidFieldException("price", "Price is mandatory and cannot be null");
        }
    }

    private boolean isMandatoryPriceNull(Book book) {
        return book.getPrice() == null && book.getPublicationDate().getYear() >= MINIMUM_YEAR_PRICE_VALIDATION;
    }

    public Boolean existsBookWithIsbn(Book book) {
        Optional<Book> foundBook = bookRepository.findByIsbn(book.getIsbn());

        if (foundBook.isEmpty()) {
            return false;
        }
        if (book.getId() == null) {
            return true;
        }

        Book existingBook = foundBook.get();
        return !book.getId().equals(existingBook.getId());
    }
}
