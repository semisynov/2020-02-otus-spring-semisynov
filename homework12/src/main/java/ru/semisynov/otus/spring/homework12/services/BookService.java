package ru.semisynov.otus.spring.homework12.services;

import ru.semisynov.otus.spring.homework12.dto.BookEntry;
import ru.semisynov.otus.spring.homework12.model.Book;

import java.util.List;

public interface BookService {

    List<BookEntry> findAllBooks();

    Book findBookById(long id);

    Book saveBook(Book book);

    void deleteBookById(long id);
}