package ru.semisynov.otus.spring.homework08.services;

public interface AuthorService {

    String getAuthorsCount();

    String findAuthorById(String id);

    String findAllAuthors();

    String addAuthor(String name);

    String deleteAuthorById(String id);
}