package ru.semisynov.otus.spring.homework07.dto;

import org.springframework.beans.factory.annotation.Value;

public interface CommentEntry {

    long getId();

    String getText();

    @Value("#{target.dateTime + ' ' + target.book.title + ' ' + target.text}")
    String getFullCommentInfo();
}