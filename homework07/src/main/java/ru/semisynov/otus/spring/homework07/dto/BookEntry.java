package ru.semisynov.otus.spring.homework07.dto;

import org.springframework.beans.factory.annotation.Value;

public interface BookEntry {

    long getId();

    String getTitle();

    @Value("#{target.title + ' ' + target.getAuthorsName()}")
    String getFullBookInfo();
}
