package io.github.library.libraryapi.controller;

import io.github.library.libraryapi.controller.DTO.AuthorDTO;
import io.github.library.libraryapi.controller.DTO.ResponseError;
import io.github.library.libraryapi.exceptions.DuplicateEntryException;
import io.github.library.libraryapi.exceptions.OperationNotAllowedException;
import io.github.library.libraryapi.mappers.AuthorMapper;
import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("authors")
public class AuthorController implements GenericController {
    AuthorService authorService;
    AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid AuthorDTO authorDTO) {

        Author authorEntity = authorService.save(authorDTO);
        URI location = getLocation(authorEntity.getId());
        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> getDetails(@PathVariable("id") String id) {
        var authorId = UUID.fromString(id);
        return authorService.findById(authorId).map(author -> {
            AuthorDTO dto = authorMapper.toDTO(author);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {

        var authorId = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(authorId);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        authorService.delete(authorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> searchAuthors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {
        List<Author> result = authorService.searchByExample(name, nationality);
        List<AuthorDTO> list =
                result.
                        stream()
                        .map(authorMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable("id") String id, @RequestBody @Valid AuthorDTO authorDTO) {
        try {
            var authorId = UUID.fromString(id);
            authorService.updateAuthor(authorId, authorDTO);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
