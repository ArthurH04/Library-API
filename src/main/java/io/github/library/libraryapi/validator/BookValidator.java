package io.github.library.libraryapi.validator;

import io.github.library.libraryapi.exceptions.DuplicateEntryException;
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

    public void validate(Book book){
        if(existsBookWithIsbn(book)){
            throw new DuplicateEntryException("Book already registered with ISBN " + book.getIsbn());
        }
    }

    public Boolean existsBookWithIsbn(Book book){
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
