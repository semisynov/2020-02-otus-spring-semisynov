package ru.semisynov.otus.spring.homework03.services;

public interface PrintService {
    void print(String message);

    void printLineWithLocale(String lineParam);

    String getLineWithLocale(String lineParam);

    String getLineWithLocale(String lineParam, Object[] args);
}
