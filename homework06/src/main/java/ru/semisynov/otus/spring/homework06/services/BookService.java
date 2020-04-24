package ru.semisynov.otus.spring.homework06.services;

public interface BookService {

    String getBooksCount();

    String findBookById(long id);

    String findAllBooks();

    String addBook(String title, String authors, String genres);

    String deleteBookById(long id);
}