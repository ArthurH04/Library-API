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

	List<String> names = new ArrayList<>(
			Arrays.asList("Elon", "Bill", "Steve", "Mark", "Jeff", "Aurora", "Steve", "Henry"));
	List<String> books = new ArrayList<>(
			Arrays.asList("Book A", "Book B", "Book C", "Book D", "Book E", "Book F", "Book G", "Book H"));

	@Test
	public void saveTest() {

		Random random = new Random();

		String randomNames = names.get(random.nextInt(names.size()));
		String randomBooks = books.get(random.nextInt(books.size()));

		BookGenre[] genre = BookGenre.values();
		int randomIndex = random.nextInt(genre.length);

		BookGenre randomGenre = genre[randomIndex];

		Author author = new Author();
		author.setName(randomNames);
		author.setNationality("Brazilian");
		author.setBirthDate(LocalDate.of(1970, 1, 1));

		authorRepository.save(author);

		Book book = new Book();
		book.setIsbn("12345");
		book.setTitle(randomBooks);
		book.setPublicationDate(LocalDate.of(2000, 1, 1));
		book.setGenre(randomGenre);
		book.setPrice(BigDecimal.valueOf(39.90));

		book.setAuthor(author);

		var savedBook = bookRepository.save(book);
		System.out.println("Saved book: " + savedBook);
	}
	
	@Test
	public void updateTitle() {
		bookRepository.updateTitle("Book AAAA", UUID.fromString("af6bf67e-6d92-4cbe-8e51-f465ffc51eba"));

	}

}
