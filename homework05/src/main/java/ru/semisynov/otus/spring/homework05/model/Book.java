package ru.semisynov.otus.spring.homework05.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class Book {

    private final long id;
    private final String title;
    private final List<Author> authors;
    private final List<Genre> genres;

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }
}