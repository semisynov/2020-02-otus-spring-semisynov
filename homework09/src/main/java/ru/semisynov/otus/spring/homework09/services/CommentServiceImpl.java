package ru.semisynov.otus.spring.homework09.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework09.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework09.model.Book;
import ru.semisynov.otus.spring.homework09.model.Comment;
import ru.semisynov.otus.spring.homework09.repositories.BookRepository;
import ru.semisynov.otus.spring.homework09.repositories.CommentRepository;

import java.time.LocalDateTime;

@Slf4j
@Service("commentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    private static final String TEXT_NOT_FOUND = "Comment not found";

    @Override
    @Transactional
    public Comment addComment(Comment comment, long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        comment.setDateTime(LocalDateTime.now());
        comment.setBook(book);
        return commentRepository.save(comment);
    }
}