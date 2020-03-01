package ru.semisynov.otus.spring.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {

    private final String lastName;
    private final String firstName;
    private int result;

    public User(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.result = 0;
    }

    public void increaseResult() {
        this.result++;
    }
}
