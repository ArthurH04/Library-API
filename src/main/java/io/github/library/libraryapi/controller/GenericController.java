package io.github.library.libraryapi.controller;

import java.net.URI;
import java.util.UUID;

public interface GenericController {
    default URI getLocation(UUID id) {
        return org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
