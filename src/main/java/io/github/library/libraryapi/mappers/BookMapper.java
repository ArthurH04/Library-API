package io.github.library.libraryapi.mappers;

import io.github.library.libraryapi.controller.DTO.AuthorDTO;
import io.github.library.libraryapi.controller.DTO.BookRegisterDTO;
import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookRegisterDTO bookDTO);
    BookRegisterDTO toDTO(Book book);
}
