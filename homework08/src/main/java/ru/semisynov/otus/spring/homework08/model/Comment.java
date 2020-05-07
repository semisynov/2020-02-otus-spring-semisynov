package ru.semisynov.otus.spring.homework08.model;

import java.time.LocalDateTime;

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

    public Comment(String text, Book book) {
        this.dateTime = LocalDateTime.now();
        this.text = text;
        this.book = book;
    }
}