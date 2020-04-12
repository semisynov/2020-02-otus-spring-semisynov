package ru.semisynov.otus.spring.homework05.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Genre {

    private final long id;
    private final String title;
}
