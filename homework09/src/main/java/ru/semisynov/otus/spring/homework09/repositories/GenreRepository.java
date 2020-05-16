package ru.semisynov.otus.spring.homework09.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.semisynov.otus.spring.homework09.model.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByTitleIgnoreCase(String title);
}