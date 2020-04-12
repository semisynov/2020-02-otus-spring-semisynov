package ru.semisynov.otus.spring.homework05.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.semisynov.otus.spring.homework05.errors.BadParameterException;
import ru.semisynov.otus.spring.homework05.services.BookService;

@ShellComponent
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private static final String BAD_ID_PARAMETER = "Book id can only be a number";

    @ShellMethod(value = "Books count", key = {"bco", "booksCount"})
    public String getBooksCount() {
        return bookService.getBooksCount();
    }

    @ShellMethod(value = "Get book by id", key = {"bci", "bookId"})
    public String getBookById(@ShellOption(help = "Book id") String id) {
        String result;
        try {
            result = bookService.getBookById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return result;
    }

//
//    @ShellMethod(value = "Get all genres", key = {"g", "genres"})
//    public String getGenresList() {
//        return genreService.getAllGenres();
//    }
//
//    @ShellMethod(value = "Create new genre", key = {"gc", "genreCreate"})
//    public String createGenre(@ShellOption(help = "Genre title") String title) {
//        return genreService.createGenre(title);
//    }
//
//    @ShellMethod(value = "Delete genre", key = {"gd", "genreDelete"})
//    public String deleteAuthor(@ShellOption(help = "Genre id") String id) {
//        String result;
//        try {
//            result = genreService.deleteGenreById(Long.parseLong(id));
//        } catch (NumberFormatException e) {
//            throw new BadParameterException(BAD_ID_PARAMETER);
//        }
//        return result;
//    }
}
