package ru.semisynov.otus.spring.homework12.dto;

import org.springframework.beans.factory.annotation.Value;

public interface CommentEntry {

    long getId();

    String getText();

    @Value("#{T(ru.semisynov.otus.spring.homework12.utils.ResultPrinter).printBookComment(target.dateTime, target.book.title, target.text)}")
    String getFullCommentInfo();
}