package ru.semisynov.otus.spring.homework08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.semisynov.otus.spring.homework08.model.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

    Optional<Genre> findByTitleIgnoreCase(String title);
}