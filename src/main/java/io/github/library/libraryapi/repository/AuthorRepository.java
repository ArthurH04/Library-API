package io.github.library.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.library.libraryapi.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

}
