package ru.semisynov.otus.spring.homework13.controllers;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.semisynov.otus.spring.homework13.model.Author;
import ru.semisynov.otus.spring.homework13.services.AuthorService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "AuthorController")
@Controller("authorController")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

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
    public String editView(@RequestParam("id") long id, @RequestParam(name = "isCreate", required = false) boolean isCreate, Model model) {
        Author author;
        if (!isCreate) {
            author = authorService.findAuthorById(id);
        } else {
            author = new Author();
        }
        model.addAttribute("author", author);
        return "author/edit";
    }

    @GetMapping("/author/view")
    public String viewPage(@RequestParam("id") long id, Model model) {
        Author author = authorService.findAuthorById(id);
        model.addAttribute("author", author);
        return "author/view";
    }

    @PostMapping("/author")
    public String postAuthor(@ModelAttribute Author author) {
        authorService.saveAuthor(author);
        return "redirect:/author";
    }

    @GetMapping("/author/delete")
    public String deleteAuthor(@RequestParam("id") long id) {
        authorService.deleteAuthorById(id);
        return "redirect:/author";
    }
}