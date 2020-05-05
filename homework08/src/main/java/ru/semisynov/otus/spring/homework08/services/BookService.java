package ru.semisynov.otus.spring.homework08.services;

public interface BookService {

    String getBooksCount();

    String findBookById(String id);

    String findAllBooks();

    String addBook(String title, String authors, String genres);

    String deleteBookById(String id);
}