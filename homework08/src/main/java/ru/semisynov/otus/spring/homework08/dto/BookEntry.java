package ru.semisynov.otus.spring.homework08.dto;

import org.springframework.beans.factory.annotation.Value;

public interface BookEntry {

    String getId();

    @Value("#{T(ru.semisynov.otus.spring.homework08.utils.ResultPrinter).printFullBookInfo(target)}")
    String getFullBookInfo();
}
