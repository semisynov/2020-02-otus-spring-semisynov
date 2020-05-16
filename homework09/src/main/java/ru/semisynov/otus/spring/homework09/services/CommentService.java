package ru.semisynov.otus.spring.homework09.services;

public interface CommentService {

    String getCommentsCount();

    String getCommentById(long id);

    String getAllComments();

    String addComment(String text, long bookId);

    String deleteCommentById(long id);

    String getAllBookComments(long bookId);
}