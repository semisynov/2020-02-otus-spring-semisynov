package ru.semisynov.otus.spring.homework08.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import ru.semisynov.otus.spring.homework08.model.Author;

import static org.apache.commons.lang3.StringUtils.trim;

public class ResultPrinter {

    public static String printFullBookInfo(String title, List<Author> authors) {
        return String.format("%s %s", title, trim(authors.stream().map(Author::getName).collect(Collectors.joining(", "))));
    }

    public static String printBookComment(LocalDateTime dateTime, String bookTitle, String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s %s %s", dateTime.format(formatter), bookTitle, text);
    }
}
