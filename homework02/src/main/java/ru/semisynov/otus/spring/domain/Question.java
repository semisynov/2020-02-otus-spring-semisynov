package ru.semisynov.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Question {

    private final String question;
    private final String rightAnswer;
}
