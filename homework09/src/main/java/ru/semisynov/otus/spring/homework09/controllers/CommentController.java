package ru.semisynov.otus.spring.homework09.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.semisynov.otus.spring.homework09.model.Comment;
import ru.semisynov.otus.spring.homework09.services.CommentService;

@Slf4j
@Controller("commentController")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment/add")
    public String editView(@RequestParam("bookId") long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        model.addAttribute("comment", new Comment());
        return "comment/edit";
    }

    @PostMapping("/comment/add")
    public String postBook(@RequestParam("bookId") long bookId, @ModelAttribute Comment comment) {
        commentService.addComment(comment, bookId);
        return "redirect:/book";
    }
}