package ru.semisynov.otus.spring.homework06.services;

public interface CommentService {

    String getCommentsCount();

    String getCommentById(long id);

    String getAllComments();

    String saveComment(String text, long bookId);

    String deleteCommentById(long id);
}