package io.github.library.libraryapi.service;

import io.github.library.libraryapi.controller.DTO.AuthorDTO;
import io.github.library.libraryapi.exceptions.OperationNotAllowedException;
import io.github.library.libraryapi.mappers.AuthorMapper;
import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.model.User;
import io.github.library.libraryapi.repository.AuthorRepository;
import io.github.library.libraryapi.repository.BookRepository;
import io.github.library.libraryapi.security.SecurityService;
import io.github.library.libraryapi.validator.AuthorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;
    private final SecurityService securityService;


    public AuthorService(AuthorRepository authorRepository, AuthorValidator authorValidator, BookRepository bookRepository, AuthorMapper authorMapper, SecurityService securityService) {
        this.authorRepository = authorRepository;
        this.authorValidator = authorValidator;
        this.bookRepository = bookRepository;
        this.authorMapper = authorMapper;
        this.securityService = securityService;
    }

    public Author save(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        User user = securityService.getLoggedUser();
        author.setUser(user);
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

    public Page<Author> searchByExample(String name, String nationality, Integer page, Integer size) {
        Author author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Author> authorExample = Example.of(author, exampleMatcher);

        Pageable pageRequest = PageRequest.of(page, size);
        return authorRepository.findAll(authorExample, pageRequest);
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
