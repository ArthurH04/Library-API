package io.github.library.libraryapi.controller.DTO;

import java.time.LocalDate;
import java.util.UUID;

import io.github.library.libraryapi.model.Author;

public record AuthorDTO(UUID id, String name, LocalDate birthDate, String nationality) {

	public Author mapToAuthor() {
		Author author = new Author();
		author.setName(this.name);
		author.setBirthDate(this.birthDate);
		author.setNationality(this.nationality);
		
		return author;
	}
	
	@Override
	public String toString() {
		return "\nName: " + name + "\nBirth date: " + birthDate + "\nNationality: " + nationality;
	}

}
