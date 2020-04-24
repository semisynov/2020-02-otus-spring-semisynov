package ru.semisynov.otus.spring.homework06.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework06.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework06.model.Book;
import ru.semisynov.otus.spring.homework06.model.Comment;
import ru.semisynov.otus.spring.homework06.repositories.BookRepository;
import ru.semisynov.otus.spring.homework06.repositories.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("commentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    private static final String TEXT_EMPTY = "There are no comments in database";
    private static final String TEXT_NOT_FOUND = "Comment not found";
    private static final String TEXT_BOOK_NOT_FOUND = "Book not found";
    private static final String TEXT_COUNT = "Comments in the database: %s";
    private static final String TEXT_NEW = "New comment text: %s, book: %s";
    private static final String TEXT_DELETED = "Successfully deleted comment id: %s, book: %s";

    @Override
    public String getCommentsCount() {
        long count = commentRepository.count();
        return count != 0 ? String.format(TEXT_COUNT, count) : TEXT_EMPTY;
    }

    @Override
    @Transactional(readOnly = true)
    public String getCommentById(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        return comment.toString();
    }

    @Override
    @Transactional(readOnly = true)
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
    public String addComment(String text, long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ItemNotFoundException(TEXT_BOOK_NOT_FOUND));
        commentRepository.add(new Comment(0L, LocalDateTime.now(), text, book));
        return String.format(TEXT_NEW, text, book.getTitle());
    }

    @Override
    @Transactional
    public String deleteCommentById(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        commentRepository.delete(comment);
        return String.format(TEXT_DELETED, comment.getId(), comment.getBook().getTitle());
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllBookComments(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ItemNotFoundException(TEXT_BOOK_NOT_FOUND));
        List<Comment> comments = book.getComments();
        String commentsResult;
        if (comments.isEmpty()) {
            commentsResult = TEXT_EMPTY;
        } else {
            commentsResult = comments.stream().map(Comment::toString).collect(Collectors.joining("\n"));
        }
        return commentsResult;
    }
}