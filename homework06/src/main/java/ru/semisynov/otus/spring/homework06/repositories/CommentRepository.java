package ru.semisynov.otus.spring.homework06.repositories;

import ru.semisynov.otus.spring.homework06.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    long count();

    Optional<Comment> findById(long id);

    List<Comment> findAll();

    void add(Comment comment);

    void delete(Comment comment);
}