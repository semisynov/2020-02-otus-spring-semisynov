package ru.semisynov.otus.spring.homework10.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.semisynov.otus.spring.homework10.dto.BookDto;
import ru.semisynov.otus.spring.homework10.model.Book;
import ru.semisynov.otus.spring.homework10.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    @GetMapping("/book")
    public ResponseEntity<List<BookDto>> getBookPage() {
        List<BookDto> books = bookService.findAllBooks().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") long id) {
        BookDto bookDto = convertToDto(bookService.findBookById(id));
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping("/book")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        Book postCreated = bookService.saveBook(book);
        return ResponseEntity.ok(convertToDto(postCreated));
    }

    @PutMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@PathVariable("id") long id, @RequestBody BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        return convertToDto(bookService.updateBook(id, book));
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteBookById(id);
    }

    @PostMapping("/book/{id}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBookComment(@PathVariable("id") long id, @RequestParam String text) {
        return convertToDto(bookService.addBookComment(id, text));
    }

    private BookDto convertToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    private Book convertToEntity(BookDto bookDto) throws ParseException {
        return modelMapper.map(bookDto, Book.class);
    }
}