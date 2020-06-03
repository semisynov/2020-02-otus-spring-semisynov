package ru.semisynov.otus.spring.homework10.services;

import ru.semisynov.otus.spring.homework10.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAllAuthors();

    Author findAuthorById(long id);

    Author saveAuthor(Author author);

    Author updateAuthor(long id, Author authorDetails);

    void deleteAuthorById(long id);
}