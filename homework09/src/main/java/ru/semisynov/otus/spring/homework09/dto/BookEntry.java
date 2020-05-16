package ru.semisynov.otus.spring.homework09.dto;

import org.springframework.beans.factory.annotation.Value;

public interface BookEntry {

    String getId();

    String getTitle();

    @Value("#{T(ru.semisynov.otus.spring.homework09.utils.ResultPrinter).printBookAuthorsInfo(target.authors)}")
    String getAuthorsInfo();

    @Value("#{T(ru.semisynov.otus.spring.homework09.utils.ResultPrinter).printBookGenresInfo(target.genres)}")
    String getGenresInfo();
}
