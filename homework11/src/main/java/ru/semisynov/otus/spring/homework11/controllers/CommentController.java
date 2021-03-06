package ru.semisynov.otus.spring.homework11.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.semisynov.otus.spring.homework11.dto.CommentDto;
import ru.semisynov.otus.spring.homework11.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework11.model.Comment;
import ru.semisynov.otus.spring.homework11.repositories.BookRepository;
import ru.semisynov.otus.spring.homework11.repositories.CommentRepository;

import java.util.Comparator;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    private static final String TEXT_NOT_FOUND = "Book not found";

    @GetMapping("/comment/{bookId}")
    public Flux<CommentDto> getBookCommentPage(@PathVariable("bookId") String bookId) {
        return commentRepository.findAllByBookId(bookId)
                .sort(Comparator.comparing(Comment::getDateTime))
                .map(this::convertToDto);
    }

    @PostMapping("/comment/{bookId}")
    public Mono<ResponseEntity<Object>> createBookComment(@PathVariable("bookId") String bookId, @RequestParam("text") String text) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(TEXT_NOT_FOUND)))
                .flatMap(book -> {
                    Comment comment = new Comment(book, text);
                    return commentRepository.save(comment)
                            .then(Mono.empty());
                });
    }

    private CommentDto convertToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }
}