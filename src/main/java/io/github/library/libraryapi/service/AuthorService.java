package io.github.library.libraryapi.service;

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

	public void delete(Author author) {authorRepository.delete(author);}
	
	public Optional<Author> findById(UUID id) {
		return authorRepository.findById(id);
	}
}
