package ru.semisynov.otus.spring.homework11.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @Field("dateTime")
    private LocalDateTime dateTime;

    @Field("text")
    private String text;

    @DBRef
    @Field("book")
    private Book book;

    public Comment(Book book, String text) {
        this.dateTime = LocalDateTime.now();
        this.book = book;
        this.text = text;
    }
}