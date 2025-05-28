package io.github.library.libraryapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.library.libraryapi.model.Author;

@SpringBootTest
public class AuthorRepositoryTest {

	@Autowired
	AuthorRepository authorRepository;

	@Test
	public void salveTest() {
		Author author = new Author();
		author.setName("Elon");
		author.setNationality("Brazilian");
		author.setBirthDate(LocalDate.of(1951, 1, 31));

		var savedAuthor = authorRepository.save(author);
		System.out.println("Saved author: " + savedAuthor);
	}
	
	@Test
	public void updateTest() {
		var id = UUID.fromString("d54ba5ba-a8be-41f4-a9d5-125e1aa31031");
		Optional<Author> author =  authorRepository.findById(id);
		
		if(author.isPresent()) {
			Author authorFound = author.get();
			System.out.println(authorFound);
			
			authorFound.setBirthDate(LocalDate.of(1960, 1, 30));
			authorRepository.save(authorFound);
		}else {
			System.out.println("Author not registered.");
		}
	}
	
	@Test
	public void listTest() {
		List<Author> authors = authorRepository.findAll();
	}
	
	@Test
	public void countAuthors() {
		System.out.println("Author count :" + authorRepository.count());
	}

}
