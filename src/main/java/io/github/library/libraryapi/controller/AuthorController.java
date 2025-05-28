package io.github.library.libraryapi.controller;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.library.libraryapi.controller.DTO.AuthorDTO;
import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.service.AuthorService;

@RestController
@RequestMapping("autores")
public class AuthorController {
	AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody AuthorDTO autor) {
		Author authorEntity = autor.mapearParaAutor();
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
}
