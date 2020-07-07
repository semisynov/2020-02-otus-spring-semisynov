package ru.semisynov.otus.spring.homework11.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.semisynov.otus.spring.homework11.model.Author;

public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {
}