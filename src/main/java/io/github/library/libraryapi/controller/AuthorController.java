package io.github.library.libraryapi.controller;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.library.libraryapi.controller.DTO.AuthorDTO;
import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.service.AuthorService;

@RestController
@RequestMapping("authors")
public class AuthorController {
	AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@PostMapping
	public ResponseEntity<Void> save(@RequestBody AuthorDTO author) {
		Author authorEntity = author.mapToAuthor();
		authorService.save(authorEntity);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(authorEntity.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("{id}")
	public ResponseEntity<AuthorDTO> getDetails(@PathVariable("id") String id) {
		var authorId = UUID.fromString(id);
		Optional<Author> authorOptional = authorService.findById(authorId);
		if (authorOptional.isPresent()) {
			Author author = authorOptional.get();
			AuthorDTO dto = new AuthorDTO(author.getId(), author.getName(), author.getBirthDate(),
					author.getNationality());
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		var authorId = UUID.fromString(id);
		Optional<Author> authorOptional = authorService.findById(authorId);
		if (authorOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		authorService.delete(authorOptional.get());
		return ResponseEntity.noContent().build();
	}
}
