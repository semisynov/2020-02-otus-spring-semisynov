package ru.semisynov.otus.spring.homework09.dto;

import org.springframework.beans.factory.annotation.Value;

public interface CommentEntry {

    String getId();

    String getText();

    @Value("#{T(ru.semisynov.otus.spring.homework09.utils.ResultPrinter).printBookComment(target.dateTime, target.book.title, target.text)}")
    String getFullCommentInfo();
}