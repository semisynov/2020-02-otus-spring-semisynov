package ru.semisynov.otus.spring.homework10.services;

import ru.semisynov.otus.spring.homework10.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAllGenres();

    Genre findGenreById(long id);

    Genre saveGenre(Genre genre);

    Genre updateGenre(long id, Genre genreDetails);

    void deleteGenreById(long id);
}