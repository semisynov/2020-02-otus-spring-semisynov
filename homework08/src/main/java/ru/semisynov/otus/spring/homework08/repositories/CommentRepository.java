package ru.semisynov.otus.spring.homework08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.semisynov.otus.spring.homework08.dto.CommentEntry;
import ru.semisynov.otus.spring.homework08.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {

    Optional<CommentEntry> findCommentById(String id);

    @Query("{}, {dateTime: 1, text: 1, book: 1}")
    List<CommentEntry> findAllComments();

    List<CommentEntry> findAllByBook_Id(String bookId);

    void deleteCommentsByBookId(String bookId);
}