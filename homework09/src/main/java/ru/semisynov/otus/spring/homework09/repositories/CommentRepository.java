package ru.semisynov.otus.spring.homework09.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.semisynov.otus.spring.homework09.dto.CommentEntry;
import ru.semisynov.otus.spring.homework09.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<CommentEntry> findCommentById(long id);

    @Query("select c from Comment c")
    List<CommentEntry> findAllComments();

    List<CommentEntry> findAllByBook_Id(long bookId);

    void deleteCommentsByBookId(String bookId);
}