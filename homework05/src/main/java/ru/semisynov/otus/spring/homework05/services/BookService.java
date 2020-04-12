package ru.semisynov.otus.spring.homework05.services;

public interface BookService {

    String getBooksCount();

    String getBookById(long id);

    String getAllBooks();

    String createBook(String title, String authors, String genres);

    String deleteBookById(long id);
}