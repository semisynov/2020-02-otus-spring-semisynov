package ru.semisynov.otus.spring.homework08.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.semisynov.otus.spring.homework08.services.CommentService;

@ShellComponent("commentController")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private static final String BAD_ID_PARAMETER = "Comment id can only be a number";
    private static final String BAD_BOOK_ID_PARAMETER = "Book id can only be a number";

    @ShellMethod(value = "Genres count", key = {"cco", "commentsCount"})
    public String getGenresCount() {
        return commentService.getCommentsCount();
    }

    @ShellMethod(value = "Get comment by id", key = {"cci", "commentId"})
    public String getCommentById(@ShellOption(help = "Comment id") String id) {
        return commentService.getCommentById(id);
    }

    @ShellMethod(value = "Get all comments", key = {"c", "comments"})
    public String getCommentsList() {
        return commentService.getAllComments();
    }

    @ShellMethod(value = "Create new comment", key = {"cc", "commentCreate"})
    public String createComment(@ShellOption(help = "Comment text") String text, @ShellOption(help = "Book id") String bookId) {
        return commentService.addComment(text, bookId);
    }

    @ShellMethod(value = "Delete comment", key = {"cd", "commentDelete"})
    public String deleteComment(@ShellOption(help = "Comment id") String id) {
        return commentService.deleteCommentById(id);
    }

    @ShellMethod(value = "Get all book comments", key = {"cb", "commentsBook"})
    public String getBookCommentsList(@ShellOption(help = "Book id") String bookId) {
        return commentService.getAllBookComments(bookId);
    }
}