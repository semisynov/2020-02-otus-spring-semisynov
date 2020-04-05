package ru.semisynov.otus.spring.homework04.errors;

public class BadParameterException extends RuntimeException {

    public BadParameterException(String message) {
        super(message);
    }
}
