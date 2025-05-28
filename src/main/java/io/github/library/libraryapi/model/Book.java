package io.github.library.libraryapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "book")
@Data
@ToString(exclude = "author")
public class Book {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "isbn", length = 20, nullable = false)
	private String isbn;

	@Column(name = "title", length = 150, nullable = false)
	private String title;

	@Column(name = "publication_date")
	private LocalDate publicationDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "genre", length = 30, nullable = false)
	private BookGenre genre;

	@Column(name = "price", precision = 18, scale = 2)
	private BigDecimal price;
	
	// Default = eager
	@ManyToOne(//cascade = CascadeType.ALL
			fetch = FetchType.LAZY)
    @JoinColumn(name = "id_author")
    private Author author;
	
	

}
