package ru.semisynov.otus.spring.homework12.services;

import ru.semisynov.otus.spring.homework12.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAllGenres();

    Genre findGenreById(long id);

    Genre saveGenre(Genre genre);

    void deleteGenreById(long id);
}