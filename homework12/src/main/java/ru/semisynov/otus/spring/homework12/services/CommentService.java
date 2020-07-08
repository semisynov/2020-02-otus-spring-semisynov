package ru.semisynov.otus.spring.homework12.services;

import ru.semisynov.otus.spring.homework12.model.Comment;

public interface CommentService {

    Comment addComment(Comment comment, long bookId);
}