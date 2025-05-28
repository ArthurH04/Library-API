package io.github.library.libraryapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.model.BookGenre;
import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.repository.AuthorRepository;
import io.github.library.libraryapi.repository.BookRepository;

@Service
public class TransactionService {
	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Transactional
	public void executar() {
		Author author = new Author();
		author.setName("Francisco");
		author.setNationality("Brazilian");
		author.setBirthDate(LocalDate.of(1970, 1, 1));

		authorRepository.saveAndFlush(author);

		Book book = new Book();
		book.setIsbn("12345");
		book.setTitle("Teste book");
		book.setPublicationDate(LocalDate.of(2000, 1, 1));
		book.setGenre(BookGenre.ACTION);
		book.setPrice(BigDecimal.valueOf(39.90));

		book.setAuthor(author);

		bookRepository.saveAndFlush(book);
		
		if(author.getName().equals("Test Francisco")) {
			throw new RuntimeException("Rollback");
		}
	}
}
