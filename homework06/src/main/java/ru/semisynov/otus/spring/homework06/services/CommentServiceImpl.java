package ru.semisynov.otus.spring.homework06.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework06.dao.BookRepository;
import ru.semisynov.otus.spring.homework06.dao.CommentRepository;
import ru.semisynov.otus.spring.homework06.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework06.model.Book;
import ru.semisynov.otus.spring.homework06.model.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("commentService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    private static final String TEXT_EMPTY = "There are no comments in database";
    private static final String TEXT_NOT_FOUND = "Comment not found";
    private static final String TEXT_BOOK_NOT_FOUND = "Book not found";
    private static final String TEXT_COUNT = "Comments in the database: %s";
    private static final String TEXT_NEW = "New comment id: %s, name: %s";
    private static final String TEXT_DELETED = "Successfully deleted comment id: %s, book: %s";

    @Override
    public String getCommentsCount() {
        long count = commentRepository.count();
        return count != 0 ? String.format(TEXT_COUNT, count) : TEXT_EMPTY;
    }

    @Override
    public String getCommentById(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        return comment.toString();
    }

    @Override
    public String getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        String commentsResult;
        if (comments.isEmpty()) {
            commentsResult = TEXT_EMPTY;
        } else {
            commentsResult = comments.stream().map(Comment::toString).collect(Collectors.joining("\n"));
        }
        return commentsResult;
    }

    @Override
    @Transactional
    public String saveComment(String text, long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ItemNotFoundException(TEXT_BOOK_NOT_FOUND));
        Comment comment = commentRepository.save(new Comment(0L, LocalDateTime.now(), text, book));
        return String.format(TEXT_NEW, comment.getId(), comment.getBook().getTitle());
    }

    @Override
    @Transactional
    public String deleteCommentById(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        commentRepository.deleteById(comment.getId());
        return String.format(TEXT_DELETED, comment.getId(), comment.getBook().getTitle());
    }
}