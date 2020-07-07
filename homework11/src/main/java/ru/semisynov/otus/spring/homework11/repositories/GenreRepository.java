package ru.semisynov.otus.spring.homework11.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.semisynov.otus.spring.homework11.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}