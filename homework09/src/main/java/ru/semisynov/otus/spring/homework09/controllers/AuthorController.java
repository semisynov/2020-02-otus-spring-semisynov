package ru.semisynov.otus.spring.homework09.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.semisynov.otus.spring.homework09.errors.BadParameterException;
import ru.semisynov.otus.spring.homework09.model.Author;
import ru.semisynov.otus.spring.homework09.services.AuthorService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller("authorController")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    private static final String BAD_ID_PARAMETER = "Author id can only be a number";

    @GetMapping("/author")
    public String listView(Model model) {
        List<Author> authors = authorService.findAllAuthors()
                .stream()
                .sorted(Comparator.comparing(Author::getId))
                .collect(Collectors.toList());
        model.addAttribute("authors", authors);
        return "author/list";
    }

    @GetMapping("/author/edit")
    public String editView(@RequestParam("id") String id, @RequestParam(name = "isCreate", required = false) boolean isCreate, Model model) {
        Author author;
        if (!isCreate) {
            try {
                author = authorService.findAuthorById(Long.parseLong(id));
            } catch (NumberFormatException e) {
                throw new BadParameterException(BAD_ID_PARAMETER);
            }
        } else {
            author = new Author();
        }
        model.addAttribute("author", author);
        return "author/edit";
    }

    @GetMapping("/author/view")
    public String viewPage(@RequestParam("id") String id, Model model) {
        try {
            Author author = authorService.findAuthorById(Long.parseLong(id));
            model.addAttribute("author", author);
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return "author/view";
    }

    @PostMapping("/author")
    public String postAuthor(@ModelAttribute Author author) {
        authorService.saveAuthor(author);
        return "redirect:/author";
    }

    @GetMapping("/author/delete")
    public String deleteAuthor(@RequestParam("id") String id) {
        try {
            authorService.deleteAuthorById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return "redirect:/author";
    }
}