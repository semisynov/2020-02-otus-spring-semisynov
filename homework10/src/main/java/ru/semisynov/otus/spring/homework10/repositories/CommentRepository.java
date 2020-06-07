package ru.semisynov.otus.spring.homework10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.semisynov.otus.spring.homework10.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}