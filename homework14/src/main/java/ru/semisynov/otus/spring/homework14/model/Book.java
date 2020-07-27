package ru.semisynov.otus.spring.homework14.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public Book(String title, List<Author> authors, List<Genre> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }
}