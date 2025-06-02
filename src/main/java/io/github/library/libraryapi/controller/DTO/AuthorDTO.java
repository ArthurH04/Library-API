package io.github.library.libraryapi.controller.DTO;

import java.time.LocalDate;
import java.util.UUID;

import io.github.library.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthorDTO(UUID id, @NotBlank(message = "Required field") String name,
                        @NotNull(message = "Required field") LocalDate birthDate,
                        @NotBlank(message = "Required field") String nationality) {

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
