package ru.semisynov.otus.spring.homework09.services;

import ru.semisynov.otus.spring.homework09.model.Comment;

public interface CommentService {

    Comment addComment(Comment comment, long bookId);
}