package ru.semisynov.otus.spring.homework08.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework08.dto.CommentEntry;
import ru.semisynov.otus.spring.homework08.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Comment;
import ru.semisynov.otus.spring.homework08.repositories.BookRepository;
import ru.semisynov.otus.spring.homework08.repositories.CommentRepository;

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
    public String getCommentById(String id) {
        CommentEntry commentEntry = commentRepository.findCommentById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        return commentEntry.getFullCommentInfo();
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllComments() {
        List<CommentEntry> comments = commentRepository.findAllComments();
        String commentsResult;
        if (comments.isEmpty()) {
            commentsResult = TEXT_EMPTY;
        } else {
            commentsResult = comments.stream().map(CommentEntry::getFullCommentInfo).collect(Collectors.joining("\n"));
        }
        return commentsResult;
    }

    @Override
    @Transactional
    public String addComment(String text, String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ItemNotFoundException(TEXT_BOOK_NOT_FOUND));
        commentRepository.save(new Comment(text, book));
        return String.format(TEXT_NEW, text, book.getTitle());
    }

    @Override
    @Transactional
    public String deleteCommentById(String id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        commentRepository.delete(comment);
        return String.format(TEXT_DELETED, comment.getId(), comment.getBook().getTitle());
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllBookComments(String bookId) {
        List<CommentEntry> comments = commentRepository.findAllByBook_Id(bookId);
        String commentsResult;
        if (comments.isEmpty()) {
            commentsResult = TEXT_EMPTY;
        } else {
            commentsResult = comments.stream().map(CommentEntry::getFullCommentInfo).collect(Collectors.joining("\n"));
        }
        return commentsResult;
    }
}