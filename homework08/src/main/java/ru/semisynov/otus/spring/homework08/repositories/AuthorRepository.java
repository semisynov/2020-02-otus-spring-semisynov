package ru.semisynov.otus.spring.homework08.repositories;

import java.util.Optional;

import ru.semisynov.otus.spring.homework08.model.Author;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findByNameIgnoreCase(String name);
}