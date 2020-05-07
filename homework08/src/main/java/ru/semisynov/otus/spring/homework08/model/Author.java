package ru.semisynov.otus.spring.homework08.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class Author {

    @Id
    private String id;

    @Field("name")
    private String name;

    public Author(String name) {
        this.name = name;
    }
}