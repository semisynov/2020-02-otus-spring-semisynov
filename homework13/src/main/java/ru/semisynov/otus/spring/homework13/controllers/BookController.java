package ru.semisynov.otus.spring.homework13.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.semisynov.otus.spring.homework13.dto.BookEntry;
import ru.semisynov.otus.spring.homework13.model.Author;
import ru.semisynov.otus.spring.homework13.model.Book;
import ru.semisynov.otus.spring.homework13.model.Genre;
import ru.semisynov.otus.spring.homework13.services.AuthorService;
import ru.semisynov.otus.spring.homework13.services.BookService;
import ru.semisynov.otus.spring.homework13.services.GenreService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "BookController")
@Controller("bookController")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/book")
    @ApiOperation("просмотр списка книг")
    public String listView(Model model) {
        List<BookEntry> books = bookService.findAllBooks()
                .stream()
                .sorted(Comparator.comparing(BookEntry::getId))
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/book/edit")
    @ApiOperation("редактирование книги")
    @PreAuthorize("hasPermission(#id, 'ru.semisynov.otus.spring.homework13.model.Book', 'WRITE')")
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
    @ApiOperation("просмотр книги")
    @PreAuthorize("hasPermission(#id, 'ru.semisynov.otus.spring.homework13.model.Book', 'READ')")
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
    @ApiOperation("создание книги")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String postBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/book";
    }

    @GetMapping("/book/delete")
    @ApiOperation("удаление книги")
    @PreAuthorize("hasPermission(#id, 'ru.semisynov.otus.spring.homework13.model.Book', 'DELETE')")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteBookById(id);
        return "redirect:/book";
    }
}