package ru.semisynov.otus.spring.homework13.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework13.dto.BookEntry;
import ru.semisynov.otus.spring.homework13.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework13.model.Book;
import ru.semisynov.otus.spring.homework13.repositories.BookRepository;

import java.util.List;

@Slf4j
@Service("bookService")
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookAclService aclBookService;

    private static final String TEXT_NOT_FOUND = "Book not found";

    @Override
    public List<BookEntry> findAllBooks() {
        return bookRepository.findAllBooks();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findBookById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        Book saved = bookRepository.save(book);
        aclBookService.createAcl(saved);
        return saved;
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        bookRepository.delete(book);
    }
}