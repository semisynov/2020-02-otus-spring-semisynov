package ru.semisynov.otus.spring.homework06.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.semisynov.otus.spring.homework06.errors.BadParameterException;
import ru.semisynov.otus.spring.homework06.services.GenreService;

@ShellComponent("genreController")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    private static final String BAD_ID_PARAMETER = "Author id can only be a number";

    @ShellMethod(value = "Genres count", key = {"gco", "genresCount"})
    public String getGenresCount() {
        return genreService.getGenresCount();
    }

    @ShellMethod(value = "Get genre by id", key = {"gci", "genreId"})
    public String getGenreById(@ShellOption(help = "Genre id") String id) {
        String result;
        try {
            result = genreService.getGenreById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return result;
    }

    @ShellMethod(value = "Get all genres", key = {"g", "genres"})
    public String getGenresList() {
        return genreService.getAllGenres();
    }

    @ShellMethod(value = "Create new genre", key = {"gc", "genreCreate"})
    public String createGenre(@ShellOption(help = "Genre title") String title) {
        return genreService.addGenre(title);
    }

    @ShellMethod(value = "Delete genre", key = {"gd", "genreDelete"})
    public String deleteAuthor(@ShellOption(help = "Genre id") String id) {
        String result;
        try {
            result = genreService.deleteGenreById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return result;
    }
}