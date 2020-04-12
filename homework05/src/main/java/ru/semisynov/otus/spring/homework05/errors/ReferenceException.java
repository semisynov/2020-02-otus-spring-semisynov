package ru.semisynov.otus.spring.homework05.errors;

public class ReferenceException extends RuntimeException {

    public ReferenceException(String message) {
        super(message);
    }
}