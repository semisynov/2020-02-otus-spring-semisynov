package ru.semisynov.otus.spring.homework08.services;

public interface CommentService {

    String getCommentsCount();

    String getCommentById(String id);

    String getAllComments();

    String addComment(String text, String bookId);

    String deleteCommentById(String id);

    String getAllBookComments(String bookId);

    String deleteCommentsByBookId(String bookId);
}