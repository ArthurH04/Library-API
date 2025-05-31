package io.github.library.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.github.library.libraryapi.controller.DTO.AuthorDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.repository.AuthorRepository;

@Service
public class AuthorService {
    AuthorRepository authorRepository;

    public AuthorService(AuthorRepository autorRepository) {
        this.authorRepository = autorRepository;
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
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

        return authorRepository.save(author);
    }
}
