package ru.semisynov.otus.spring.homework05.errors;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String message) {
        super(message);
    }
}