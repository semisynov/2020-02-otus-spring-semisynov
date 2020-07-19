package ru.semisynov.otus.spring.homework13.services;

import ru.semisynov.otus.spring.homework13.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAllAuthors();

    Author findAuthorById(long id);

    Author saveAuthor(Author author);

    void deleteAuthorById(long id);
}