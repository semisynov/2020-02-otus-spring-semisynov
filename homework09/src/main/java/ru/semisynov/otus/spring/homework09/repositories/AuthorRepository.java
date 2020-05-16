package ru.semisynov.otus.spring.homework09.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.semisynov.otus.spring.homework09.model.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameIgnoreCase(String name);
}