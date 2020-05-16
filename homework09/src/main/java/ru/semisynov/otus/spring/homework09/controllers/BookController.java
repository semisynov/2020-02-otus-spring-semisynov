package ru.semisynov.otus.spring.homework09.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.semisynov.otus.spring.homework09.dto.BookEntry;
import ru.semisynov.otus.spring.homework09.errors.BadParameterException;
import ru.semisynov.otus.spring.homework09.model.Author;
import ru.semisynov.otus.spring.homework09.model.Book;
import ru.semisynov.otus.spring.homework09.model.Genre;
import ru.semisynov.otus.spring.homework09.services.AuthorService;
import ru.semisynov.otus.spring.homework09.services.BookService;
import ru.semisynov.otus.spring.homework09.services.GenreService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller("bookController")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    private static final String BAD_ID_PARAMETER = "Book id can only be a number";

    @GetMapping("/book")
    public String listView(Model model) {
        List<BookEntry> books = bookService.findAllBooks()
                .stream()
                .sorted(Comparator.comparing(BookEntry::getId))
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/book/edit")
    public String editView(@RequestParam("id") String id, @RequestParam(name = "isCreate", required = false) boolean isCreate, Model model) {
        Book book;
        if (!isCreate) {
            try {
                book = bookService.findBookById(Long.parseLong(id));
            } catch (NumberFormatException e) {
                throw new BadParameterException(BAD_ID_PARAMETER);
            }
        } else {
            book = new Book();
        }
        model.addAttribute("book", book);

        List<Author> authors = authorService.findAllAuthors();
        model.addAttribute("authors", authors);

        List<Genre> genres = genreService.findAllGenres();
        model.addAttribute("genres", genres);

        return "book/edit";
    }

    @GetMapping("/book/view")
    public String viewPage(@RequestParam("id") String id, Model model) {
        try {
            Book book = bookService.findBookById(Long.parseLong(id));
            model.addAttribute("book", book);

            List<Author> authors = authorService.findAllAuthors();
            model.addAttribute("authors", authors);

            List<Genre> genres = genreService.findAllGenres();
            model.addAttribute("genres", genres);
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return "book/view";
    }

    @PostMapping("/book")
    public String postBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/book";
    }

    @GetMapping("/book/delete")
    public String deleteBook(@RequestParam("id") String id) {
        try {
            bookService.deleteBookById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return "redirect:/book";
    }
}