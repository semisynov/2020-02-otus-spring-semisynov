package ru.semisynov.otus.spring.homework05.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Book {

    private final long id;
    private final String name;
    private final long authorId;
}
