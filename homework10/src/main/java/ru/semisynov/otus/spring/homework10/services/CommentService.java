package ru.semisynov.otus.spring.homework10.services;

import ru.semisynov.otus.spring.homework10.model.Book;
import ru.semisynov.otus.spring.homework10.model.Comment;

public interface CommentService {

    Comment addComment(Book book, String text);
}