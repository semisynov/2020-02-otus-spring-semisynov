package ru.semisynov.otus.spring.homework08.dto;

import org.springframework.beans.factory.annotation.Value;

public interface CommentEntry {

    String getId();

    @Value("#{T(ru.semisynov.otus.spring.homework08.utils.ResultPrinter).printBookComment(target)}")
    String getFullCommentInfo();
}