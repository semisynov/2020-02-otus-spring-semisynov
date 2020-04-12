package ru.semisynov.otus.spring.homework05.services;

public interface AuthorService {

    String getAuthorsCount();

    String getAuthorById(long id);

    String getAllAuthors();

    String createAuthor(String name);

    String deleteAuthorById(long id);
}