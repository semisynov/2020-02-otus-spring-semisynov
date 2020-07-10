package ru.semisynov.otus.spring.homework12.utils;

import ru.semisynov.otus.spring.homework12.model.Author;
import ru.semisynov.otus.spring.homework12.model.Genre;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.trim;

public class ResultPrinter {

    public static String printBookAuthorsInfo(List<Author> authors) {
        return String.format("%s", trim(authors.stream().map(Author::getName).collect(Collectors.joining(", "))));
    }

    public static String printBookGenresInfo(List<Genre> genres) {
        return String.format("%s", trim(genres.stream().map(Genre::getTitle).collect(Collectors.joining(", "))));
    }

    public static String printBookComment(LocalDateTime dateTime, String bookTitle, String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s %s %s", dateTime.format(formatter), bookTitle, text);
    }
}
