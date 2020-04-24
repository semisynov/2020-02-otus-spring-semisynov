package ru.semisynov.otus.spring.homework06.services;

public interface GenreService {

    String getGenresCount();

    String getGenreById(long id);

    String getAllGenres();

    String addGenre(String title);

    String deleteGenreById(long id);
}