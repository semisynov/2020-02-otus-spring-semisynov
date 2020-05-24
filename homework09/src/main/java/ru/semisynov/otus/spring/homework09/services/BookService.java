package ru.semisynov.otus.spring.homework09.services;

import ru.semisynov.otus.spring.homework09.dto.BookEntry;
import ru.semisynov.otus.spring.homework09.model.Book;

import java.util.List;

public interface BookService {

    List<BookEntry> findAllBooks();

    Book findBookById(long id);

    Book saveBook(Book book);

    void deleteBookById(long id);
}