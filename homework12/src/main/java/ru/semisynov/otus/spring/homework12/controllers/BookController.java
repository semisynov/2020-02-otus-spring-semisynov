package ru.semisynov.otus.spring.homework12.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.semisynov.otus.spring.homework12.dto.BookEntry;
import ru.semisynov.otus.spring.homework12.model.Author;
import ru.semisynov.otus.spring.homework12.model.Book;
import ru.semisynov.otus.spring.homework12.model.Genre;
import ru.semisynov.otus.spring.homework12.services.AuthorService;
import ru.semisynov.otus.spring.homework12.services.BookService;
import ru.semisynov.otus.spring.homework12.services.GenreService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller("bookController")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

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
    public String editView(@RequestParam("id") long id, @RequestParam(name = "isCreate", required = false) boolean isCreate, Model model) {
        Book book;
        if (!isCreate) {
            book = bookService.findBookById(id);
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
    public String viewPage(@RequestParam("id") long id, Model model) {
        Book book = bookService.findBookById(id);
        model.addAttribute("book", book);

        List<Author> authors = authorService.findAllAuthors();
        model.addAttribute("authors", authors);

        List<Genre> genres = genreService.findAllGenres();
        model.addAttribute("genres", genres);
        return "book/view";
    }

    @PostMapping("/book")
    public String postBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/book";
    }

    @GetMapping("/book/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteBookById(id);
        return "redirect:/book";
    }
}