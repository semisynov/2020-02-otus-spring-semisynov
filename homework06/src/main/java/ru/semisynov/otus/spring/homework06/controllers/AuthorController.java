package ru.semisynov.otus.spring.homework06.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.semisynov.otus.spring.homework06.errors.BadParameterException;
import ru.semisynov.otus.spring.homework06.services.AuthorService;

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
        String result;
        try {
            result = authorService.findAuthorById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return result;
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
        String result;
        try {
            result = authorService.deleteAuthorById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return result;
    }
}