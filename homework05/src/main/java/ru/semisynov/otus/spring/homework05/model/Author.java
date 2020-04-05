package ru.semisynov.otus.spring.homework05.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Author {

    private final long id;
    private final String lastName;
    private final String firstName;
    private final long genreId;
}
