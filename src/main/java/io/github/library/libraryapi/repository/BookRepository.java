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
 * @see BookRepositoryTest
 * 
 */

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
	List<Book> findByAuthor(Author author);

	List<Book> findByTitleContainingIgnoreCase(String title);

	@Query("SELECT b from Book as b order by b.title, b.price")
	List<Book> listAll();
	
	@Query("SELECT a from Book b JOIN b.author a")
	List<Author> listAuthors();
	
	@Query("""
			SELECT b.genre
			FROM Book b
			JOIN b.author a
			WHERE a.nationality = 'Brazilian'
			ORDER BY b.genre
			""")
	List<String> listGenresBrazilianAuthors();
	
	@Query("select b from Book b where b.genre = :genre order by :paramOrder")
	List<Book> findByGenreParam(@Param("genre") BookGenre bookGenre, @Param("paramOrder") String propertyName);
	
	@Query("select distinct b from Book b where b.genre = ?1 order by ?2")
	List<Book> findByGenrePositionalParam(BookGenre bookGenre, String propertyName);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Book WHERE genre = ?1")
	void deleteByGenre(BookGenre bookGenre);
	
	@Modifying
	@Transactional
	@Query("UPDATE Book SET title = ?1 WHERE id = ?2")
	void updateTitle(String title, UUID id);


	boolean existsByAuthor(Author author);
}
