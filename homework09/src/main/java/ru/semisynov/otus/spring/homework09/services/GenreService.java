package ru.semisynov.otus.spring.homework09.services;

import ru.semisynov.otus.spring.homework09.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAllGenres();

    Genre findGenreById(long id);

    Genre saveGenre(Genre genre);

    void deleteGenreById(long id);
}