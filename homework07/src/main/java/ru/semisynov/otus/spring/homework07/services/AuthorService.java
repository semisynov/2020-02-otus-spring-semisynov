package ru.semisynov.otus.spring.homework07.services;

public interface AuthorService {

    String getAuthorsCount();

    String findAuthorById(long id);

    String findAllAuthors();

    String addAuthor(String name);

    String deleteAuthorById(long id);
}