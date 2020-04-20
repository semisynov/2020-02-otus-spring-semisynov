package ru.semisynov.otus.spring.homework06.services;

public interface AuthorService {

    String getAuthorsCount();

    String findAuthorById(long id);

    String findAllAuthors();

    String addAuthor(String name);

    String deleteAuthorById(long id);
}