package ru.semisynov.otus.spring.homework11.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.semisynov.otus.spring.homework11.dto.BookDto;
import ru.semisynov.otus.spring.homework11.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework11.model.Book;
import ru.semisynov.otus.spring.homework11.repositories.BookRepository;
import ru.semisynov.otus.spring.homework11.repositories.CommentRepository;

import java.util.Comparator;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    private static final String TEXT_NOT_FOUND = "Book not found";

    @GetMapping("/book")
    public Flux<BookDto> getBookPage() {
        return bookRepository.findAll()
                .sort(Comparator.comparing(Book::getTitle))
                .map(this::convertToDto);
    }

    @GetMapping("/book/{id}")
    public Mono<BookDto> getBookById(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(TEXT_NOT_FOUND)))
                .map(this::convertToDto);
    }

    @PostMapping("/book")
    public Mono<BookDto> createBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(convertToEntity(bookDto))
                .map(this::convertToDto);
    }

    @PutMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BookDto> updateBook(@PathVariable("id") String id, @RequestBody BookDto bookDto) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(TEXT_NOT_FOUND)))
                .flatMap(b -> bookRepository.save(convertToEntity(bookDto)))
                .map(this::convertToDto);
    }

    @DeleteMapping("/book/{id}")
    public Mono<ResponseEntity<Object>> deleteBook(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(TEXT_NOT_FOUND)))
                .flatMap(b -> commentRepository.deleteAllByBook(b)
                        .then(bookRepository.delete(b))
                        .then(Mono.empty()));
    }

    private BookDto convertToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    private Book convertToEntity(BookDto bookDto) throws ParseException {
        return modelMapper.map(bookDto, Book.class);
    }
}