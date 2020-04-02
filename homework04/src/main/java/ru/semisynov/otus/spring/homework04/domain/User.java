package ru.semisynov.otus.spring.homework04.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {

    private String lastName;
    private String firstName;
    private int result;

    public User() {
        this.result = 0;
    }

    public void increaseResult() {
        this.result++;
    }
}
