package ru.semisynov.otus.spring.homework08.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Field("title")
    private String title;

    @DBRef
    @Field("authors")
    private List<Author> authors;

    @DBRef
    @Field("genres")
    private List<Genre> genres;

    @DBRef
    @Field("comments")
    private List<Comment> comments;

    public Book(String title, List<Author> authors, List<Genre> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }
}