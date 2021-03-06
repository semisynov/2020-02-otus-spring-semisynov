package ru.semisynov.otus.spring.homework08.dto;

import org.springframework.beans.factory.annotation.Value;

public interface CommentEntry {

    String getId();

    String getText();

    @Value("#{T(ru.semisynov.otus.spring.homework08.utils.ResultPrinter).printBookComment(target.dateTime, target.book.title, target.text)}")
    String getFullCommentInfo();
}