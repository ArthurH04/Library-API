package io.github.library.libraryapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.library.libraryapi.model.Author;
import io.github.library.libraryapi.model.BookGenre;
import io.github.library.libraryapi.model.Book;

/*
 * @see LivroRepositoryTest
 * 
 */

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
	List<Book> findByAuthor(Author autor);

	List<Book> findByTitleContainingIgnoreCase(String title);

	@Query("SELECT l from Book as l order by l.title, l.price")
	List<Book> listAll();
	
	@Query("SELECT a from Book l JOIN l.author a")
	List<Author> listAuthors();
	
	@Query("""
			SELECT l.genre, l.Book
			FROM Book l
			JOIN l.author a
			WHERE a.nationality = 'Brazilian'
			ORDER BY l.genre
			""")
	List<String> listGenresBrazilianAuthors();
	
	@Query("select l from Book l where l.genre = :genre order by :paramOrder")
	List<Book> findByGenreParam(@Param("genre") BookGenre bookGenre, @Param("paramOrder") String propertyName);
	
	@Query("select distinct l from Book l where l.genre = ?1 order by ?2")
	List<Book> findByGenrePositionalParam(BookGenre bookGenre, String propertyName);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Book WHERE genre = ?1")
	void deleteByGenero(BookGenre bookGenre);
	
	@Modifying
	@Transactional
	@Query("UPDATE Book SET title = ?1 WHERE id = ?2")
	void updateTitle(String title, UUID id);
}
