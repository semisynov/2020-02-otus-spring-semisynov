package ru.semisynov.otus.spring.homework12.dto;

import org.springframework.beans.factory.annotation.Value;

public interface BookEntry {

    long getId();

    String getTitle();

    @Value("#{T(ru.semisynov.otus.spring.homework12.utils.ResultPrinter).printBookAuthorsInfo(target.authors)}")
    String getAuthorsInfo();

    @Value("#{T(ru.semisynov.otus.spring.homework12.utils.ResultPrinter).printBookGenresInfo(target.genres)}")
    String getGenresInfo();
}
