package ru.semisynov.otus.spring.homework08.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genres")
public class Genre {

    @Id
    private String id;

    @Field("title")
    private String title;

    public Genre(String title) {
        this.title = title;
    }
}