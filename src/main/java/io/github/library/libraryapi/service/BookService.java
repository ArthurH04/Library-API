package io.github.library.libraryapi.service;

import io.github.library.libraryapi.controller.DTO.BookRegisterDTO;
import io.github.library.libraryapi.mappers.BookMapper;
import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.repository.AuthorRepository;
import io.github.library.libraryapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }


    public Book save(BookRegisterDTO bookDTO){
        Book book = bookMapper.toEntity(bookDTO);
        UUID authorId = bookDTO.author_id();
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));

        book.setAuthor(author);

        return bookRepository.save(book);
    }


}
