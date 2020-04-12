package ru.semisynov.otus.spring.homework05.dao;

import ru.semisynov.otus.spring.homework05.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    long count();

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    long insert(Genre genre);

    void deleteById(long id);

    List<Genre> getByBookId(long id);

    Optional<Genre> getByTitle(String title);
}