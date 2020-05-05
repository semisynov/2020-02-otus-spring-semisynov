package ru.semisynov.otus.spring.homework08.utils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import ru.semisynov.otus.spring.homework08.model.Author;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Comment;

import static org.apache.commons.lang3.StringUtils.trim;

public class ResultPrinter {

    public static String printFullBookInfo(Book book) {
        List<Author> authors = book.getAuthors();
        return String.format("%s %s", book.getTitle(), trim(authors.stream().map(Author::getName).collect(Collectors.joining(", "))));
    }

    public static String printBookComment(Comment comment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s %s %s", comment.getDateTime().format(formatter), comment.getBook().getTitle(), comment.getText());
    }
}
