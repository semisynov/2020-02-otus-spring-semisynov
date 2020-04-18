package ru.semisynov.otus.spring.homework06.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.semisynov.otus.spring.homework06.errors.BadParameterException;
import ru.semisynov.otus.spring.homework06.services.CommentService;

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
        String result;
        try {
            result = commentService.getCommentById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return result;
    }

    @ShellMethod(value = "Get all comments", key = {"c", "comments"})
    public String getCommentsList() {
        return commentService.getAllComments();
    }

    @ShellMethod(value = "Create new comment", key = {"cc", "commentCreate"})
    public String createComment(@ShellOption(help = "Comment text") String text, @ShellOption(help = "Book id") String bookId) {
        String result;
        try {
            result = commentService.saveComment(text, Long.parseLong(bookId));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_BOOK_ID_PARAMETER);
        }
        return result;
    }

    @ShellMethod(value = "Delete comment", key = {"cd", "commentDelete"})
    public String deleteComment(@ShellOption(help = "Comment id") String id) {
        String result;
        try {
            result = commentService.deleteCommentById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadParameterException(BAD_ID_PARAMETER);
        }
        return result;
    }
}