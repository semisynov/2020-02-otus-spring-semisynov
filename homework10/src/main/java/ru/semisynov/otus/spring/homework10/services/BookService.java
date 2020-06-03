package ru.semisynov.otus.spring.homework10.services;

import ru.semisynov.otus.spring.homework10.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks();

    Book findBookById(long id);

    Book saveBook(Book book);

    Book updateBook(long id, Book bookDetails);

    void deleteBookById(long id);

    void addBookComment(long id, String text);
}