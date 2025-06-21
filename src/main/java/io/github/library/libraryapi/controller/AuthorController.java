package io.github.library.libraryapi.controller;

import io.github.library.libraryapi.controller.DTO.AuthorDTO;
import io.github.library.libraryapi.mappers.AuthorMapper;
import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("authors")
public class AuthorController implements GenericController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO authorDTO) {
        Author authorEntity = authorService.save(authorDTO);
        URI location = getLocation(authorEntity.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AuthorDTO> getDetails(@PathVariable("id") String id) {
        var authorId = UUID.fromString(id);
        return authorService.findById(authorId).map(author -> {
            AuthorDTO dto = authorMapper.toDTO(author);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {

        var authorId = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(authorId);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        authorService.delete(authorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<AuthorDTO>> searchAuthors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        {
            Page<Author> result = authorService.searchByExample(name, nationality, page, size);
            Page<AuthorDTO> map = result.map(authorMapper::toDTO);
            return ResponseEntity.ok(map);
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateAuthor(@PathVariable("id") String id, @RequestBody @Valid AuthorDTO authorDTO) {
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
