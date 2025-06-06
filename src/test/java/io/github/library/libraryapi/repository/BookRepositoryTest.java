package io.github.library.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.model.BookGenre;
import io.github.library.libraryapi.model.Book;

@SpringBootTest
public class BookRepositoryTest {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	AuthorRepository authorRepository;

	@Test
	public void saveTest() {
		Book book = new Book();
		book.setIsbn("12345");
		book.setTitle("Star Wars");
		book.setPublicationDate(LocalDate.of(2000, 1, 1));
		book.setGenre(BookGenre.FICTION);
		book.setPrice(BigDecimal.valueOf(39.90));

		Author author = authorRepository.findById(UUID.fromString("7c625dbc-29b3-4722-9595-03bc4309ec49")).orElse(null);
		book.setAuthor(author);
		var savedBook = bookRepository.save(book);
		System.out.println("Saved book: " + savedBook);
	}
	
	@Test
	public void updateTitle() {
		bookRepository.updateTitle("Book AAAA", UUID.fromString("af6bf67e-6d92-4cbe-8e51-f465ffc51eba"));

	}

}
