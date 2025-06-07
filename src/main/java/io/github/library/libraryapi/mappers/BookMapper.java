package io.github.library.libraryapi.mappers;

import io.github.library.libraryapi.controller.DTO.BookRegisterDTO;
import io.github.library.libraryapi.controller.DTO.BookSearchResultDTO;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java(authorRepository.findById(bookDTO.author_id()).orElse(null))")
    public abstract Book toEntity(BookRegisterDTO bookDTO);

    public abstract BookSearchResultDTO toDto(Book book);
}
