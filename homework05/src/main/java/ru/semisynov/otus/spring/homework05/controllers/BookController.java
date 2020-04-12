package ru.semisynov.otus.spring.homework05.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.semisynov.otus.spring.homework05.errors.BadParameterException;
import ru.semisynov.otus.spring.homework05.services.BookService;

@ShellComponent("bookController")
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

    @ShellMethod(value = "Get all books", key = {"b", "books"})
    public String getBooksList() {
        return bookService.getAllBooks();
    }

    @ShellMethod(value = "Create new book", key = {"bc", "bookCreate"})
    public String createBook(@ShellOption(help = "Book title") String title, String authors, String genres) {
        return bookService.createBook(title, authors, genres);
    }

    @ShellMethod(value = "Delete book", key = {"bd", "bookDelete"})
    public String deleteBook(@ShellOption(help = "Book id") String id) {
        String result;
        try {
            result = bookService.deleteBookById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return result;
    }
}