package io.github.library.libraryapi.repository.specs;

import io.github.library.libraryapi.model.Book;
import io.github.library.libraryapi.model.BookGenre;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {
    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> authorNameLike(String authorName) {
        // SELECT *
        // FROM book b
        // JOIN author a ON a.id = b.author_id
        // WHERE UPPER(a.name) LIKE UPPER('%authorName%')
        return (root, query, cb) -> {
            Join<Object, Object> authorJoin = root.join("author", JoinType.INNER);
            return cb.like(cb.upper(authorJoin.get("name")), "%" + authorName.toUpperCase() + "%");
        };
    }

    public static Specification<Book> genreEqual(BookGenre genre) {
        return (root, query, cb) -> cb.equal(root.get("genre"), genre);
    }

    public static Specification<Book> publicationDate(Integer publicationDate) {
        // to_char(publication_date, 'YYYY') = :publicationDate
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class, root.get("publicationDate"),
                        cb.literal("YYYY")), publicationDate.toString());
    }
}
