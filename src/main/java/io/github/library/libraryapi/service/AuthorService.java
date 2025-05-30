package io.github.library.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
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
}
