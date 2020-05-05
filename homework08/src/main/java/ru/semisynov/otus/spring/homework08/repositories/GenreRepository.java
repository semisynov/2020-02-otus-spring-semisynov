package ru.semisynov.otus.spring.homework08.repositories;

import java.util.Optional;

import ru.semisynov.otus.spring.homework08.model.Genre;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {

    Optional<Genre> findByTitleIgnoreCase(String title);
}