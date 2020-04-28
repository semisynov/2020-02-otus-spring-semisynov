package ru.semisynov.otus.spring.homework07.services;

public interface GenreService {

    String getGenresCount();

    String getGenreById(long id);

    String getAllGenres();

    String addGenre(String title);

    String deleteGenreById(long id);
}