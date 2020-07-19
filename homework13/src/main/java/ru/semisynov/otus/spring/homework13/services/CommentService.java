package ru.semisynov.otus.spring.homework13.services;

import ru.semisynov.otus.spring.homework13.model.Comment;

public interface CommentService {

    Comment addComment(Comment comment, long bookId);
}