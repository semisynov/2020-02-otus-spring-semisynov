package ru.semisynov.otus.spring.homework08.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.semisynov.otus.spring.homework08.services.AuthorService;

@ShellComponent("authorController")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    private static final String BAD_ID_PARAMETER = "Author id can only be a number";

    @ShellMethod(value = "Authors count", key = {"aco", "authorsCount"})
    public String getAuthorsCount() {
        return authorService.getAuthorsCount();
    }

    @ShellMethod(value = "Get author by id", key = {"aci", "authorId"})
    public String getAuthorById(@ShellOption(help = "Author id") String id) {
        return authorService.findAuthorById(id);
    }

    @ShellMethod(value = "Get all authors", key = {"a", "authors"})
    public String getAuthorsList() {
        return authorService.findAllAuthors();
    }

    @ShellMethod(value = "Create new author", key = {"ac", "authorCreate"})
    public String createAuthor(@ShellOption(help = "Author name") String name) {
        return authorService.addAuthor(name);
    }

    @ShellMethod(value = "Delete author", key = {"ad", "authorDelete"})
    public String deleteAuthor(@ShellOption(help = "Author id") String id) {
        return authorService.deleteAuthorById(id);
    }
}