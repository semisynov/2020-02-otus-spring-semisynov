package ru.semisynov.otus.spring.homework10.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework10.model.Book;
import ru.semisynov.otus.spring.homework10.model.Comment;
import ru.semisynov.otus.spring.homework10.repositories.CommentRepository;

@Slf4j
@Service("commentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Comment addComment(Book book, String text) {
        Comment comment = new Comment(book, text);
        return commentRepository.save(comment);
    }
}