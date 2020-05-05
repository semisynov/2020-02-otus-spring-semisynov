package ru.semisynov.otus.spring.homework08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.semisynov.otus.spring.homework08.model.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findByNameIgnoreCase(String name);
}