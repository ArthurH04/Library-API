package io.github.library.libraryapi.mappers;

import io.github.library.libraryapi.controller.DTO.AuthorDTO;
import io.github.library.libraryapi.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorDTO authorDTO);
    AuthorDTO toDTO(Author author);
}
