package ru.semisynov.otus.spring.homework05.services;

public interface GenreService {

    String getGenresCount();

    String getGenreById(long id);

    String getAllGenres();

    String createGenre(String title);

    String deleteGenreById(long id);
}