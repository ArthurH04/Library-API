package io.github.library.libraryapi.controller;

import io.github.library.libraryapi.controller.DTO.UserDTO;
import io.github.library.libraryapi.mappers.UserMapper;
import io.github.library.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UserDTO userDTO) {
        var user = userMapper.toEntity(userDTO);
        service.save(user);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("login") String id) {
        UUID userId = UUID.fromString(id);
        var bookOptional = service.findById(userId);
        if (bookOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(bookOptional.get());
        return ResponseEntity.noContent().build();
    }
}
