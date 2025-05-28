package io.github.library.libraryapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "author", schema = "public")
@Data
@ToString(exclude = "books")
@EntityListeners(AuditingEntityListener.class)
public class Author {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate;

	@Column(name = "nationality", length = 50, nullable = false)
	private String nationality;

	// Default = lazy
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<Book> books;
	
	@CreatedDate
	@Column(name = "register_date")
	private LocalDateTime registerDate;
	
	@LastModifiedDate
	@Column(name = "update_date")
	private LocalDateTime updateDate;
	
	@Column(name = "user_id")
	private UUID user_id;

	public Author() {
	}

	public Author(String name, LocalDate birthDate, String nationality) {
		this.name = name;
		this.birthDate = birthDate;
		this.nationality = nationality;
	}
}