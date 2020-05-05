package ru.semisynov.otus.spring.homework08.services;

public interface GenreService {

    String getGenresCount();

    String getGenreById(String id);

    String getAllGenres();

    String addGenre(String title);

    String deleteGenreById(String id);
}