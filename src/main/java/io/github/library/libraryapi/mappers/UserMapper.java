package io.github.library.libraryapi.mappers;

import io.github.library.libraryapi.controller.DTO.UserDTO;
import io.github.library.libraryapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
}
