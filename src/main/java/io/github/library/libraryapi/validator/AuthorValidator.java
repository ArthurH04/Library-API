package io.github.library.libraryapi.validator;

import io.github.library.libraryapi.exceptions.DuplicateEntryException;
import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void validate(Author author){
        if (existsRegisteredAuthor(author)) {
            throw new DuplicateEntryException("Author already registered");
        }
    }
    public boolean existsRegisteredAuthor(Author author) {
        Optional<Author> foundAuthor = authorRepository.findByNameAndBirthDateAndNationality(
                author.getName(), author.getBirthDate(), author.getNationality()
        );

        if (foundAuthor.isEmpty()) {
            return false;
        }

        if (author.getId() == null) {
            return true;
        }

        Author existingAuthor = foundAuthor.get();

        return !author.getId().equals(existingAuthor.getId());
    }

}
