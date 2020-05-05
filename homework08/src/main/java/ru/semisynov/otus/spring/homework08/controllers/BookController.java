package ru.semisynov.otus.spring.homework08.controllers;

import lombok.RequiredArgsConstructor;
import ru.semisynov.otus.spring.homework08.services.BookService;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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
        return bookService.findBookById(id);
    }

    @ShellMethod(value = "Get all books", key = {"b", "books"})
    public String getBooksList() {
        return bookService.findAllBooks();
    }

    @ShellMethod(value = "Create new book", key = {"bc", "bookCreate"})
    public String createBook(@ShellOption(help = "Book title") String title, String authors, String genres) {
        return bookService.addBook(title, authors, genres);
    }

    @ShellMethod(value = "Delete book", key = {"bd", "bookDelete"})
    public String deleteBook(@ShellOption(help = "Book id") String id) {
        return bookService.deleteBookById(id);
    }
}