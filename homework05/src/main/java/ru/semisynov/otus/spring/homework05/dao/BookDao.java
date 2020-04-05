package ru.semisynov.otus.spring.homework05.dao;

import ru.semisynov.otus.spring.homework05.model.Book;

public interface BookDao {

    int count();
    void insert(Book book);
    //Book getById(long id);
}
