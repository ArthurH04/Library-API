package io.github.library.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.github.library.libraryapi.controller.DTO.AuthorDTO;
import io.github.library.libraryapi.exceptions.OperationNotAllowedException;
import io.github.library.libraryapi.mappers.AuthorMapper;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.repository.BookRepository;
import io.github.library.libraryapi.validator.AuthorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.repository.AuthorRepository;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;


    public AuthorService(AuthorRepository authorRepository, AuthorValidator authorValidator, BookRepository bookRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorValidator = authorValidator;
        this.bookRepository = bookRepository;
        this.authorMapper = authorMapper;
    }

    public Author save(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorValidator.validate(author);
        return authorRepository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    public void delete(Author author) {
        if (hasABook(author)) {
            throw new OperationNotAllowedException("The author has registered books");
        }
        authorRepository.delete(author);
    }

   /* public List<Author> search(String name, String nationality) {
        if (name != null && nationality != null) {
            return authorRepository.findByNameAndNationality(name, nationality);
        }

        if (name != null) {
            return authorRepository.findByName(name);
        }

        if (nationality != null) {
            return authorRepository.findByNationality(nationality);
        }
        return authorRepository.findAll();
    }*/

    public List<Author> searchByExample(String name, String nationality) {
        Author author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Author> authorExample = Example.of(author, exampleMatcher);
        return authorRepository.findAll(authorExample);
    }

    public Author updateAuthor(UUID authorId, AuthorDTO authorDTO) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if (authorOptional.isEmpty()) {
            throw new EntityNotFoundException("Author not found with id: " + authorId);
        }
        Author author = authorOptional.get();
        author.setName(authorDTO.name());
        author.setNationality(authorDTO.nationality());
        author.setBirthDate(authorDTO.birthDate());

        authorValidator.validate(author);
        return authorRepository.save(author);
    }

    public boolean hasABook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
