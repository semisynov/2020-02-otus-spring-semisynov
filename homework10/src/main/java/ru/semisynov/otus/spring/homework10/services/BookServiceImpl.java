package ru.semisynov.otus.spring.homework10.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework10.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework10.model.Book;
import ru.semisynov.otus.spring.homework10.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service("bookService")
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentService commentService;

    private static final String TEXT_NOT_FOUND = "Book not found";

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findBookById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(long id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        book.setTitle(bookDetails.getTitle());
        book.setAuthors(bookDetails.getAuthors());
        book.setGenres(bookDetails.getGenres());
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        bookRepository.delete(book);
    }

    @Override
    @Transactional
    public Book addBookComment(long id, String text) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        bookOptional.ifPresent(book -> commentService.addComment(book, text));
        return bookOptional.orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
    }
}