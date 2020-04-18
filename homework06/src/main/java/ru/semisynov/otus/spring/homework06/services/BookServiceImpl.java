package ru.semisynov.otus.spring.homework06.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework06.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework06.model.Author;
import ru.semisynov.otus.spring.homework06.model.Book;
import ru.semisynov.otus.spring.homework06.model.Genre;
import ru.semisynov.otus.spring.homework06.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework06.repositories.BookRepository;
import ru.semisynov.otus.spring.homework06.repositories.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("bookService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    private static final String TEXT_EMPTY = "There are no books in database";
    private static final String TEXT_NOT_FOUND = "Book not found";
    private static final String TEXT_COUNT = "Books in the database: %s";
    private static final String TEXT_NEW = "New book id: %s, name: %s";
    private static final String TEXT_DELETED = "Successfully deleted book id: %s, name: %s";
    private static final String TEXT_AUTHOR_NOT_FOUND = "Author %s not found";
    private static final String TEXT_GENRE_NOT_FOUND = "Genre %s not found";

    @Override
    public String getBooksCount() {
        long count = bookRepository.count();
        return count != 0 ? String.format(TEXT_COUNT, count) : TEXT_EMPTY;
    }

    @Override
    public String findBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        return book.toString();
    }

    @Override
    public String findAllBooks() {
        List<Book> books = bookRepository.findAll();

        String booksResult;
        if (books.isEmpty()) {
            booksResult = TEXT_EMPTY;
        } else {
            booksResult = books.stream().map(Book::toString).collect(Collectors.joining("\n"));
        }
        return booksResult;
    }

    @Override
    @Transactional
    public String saveBook(String title, String authors, String genres) {
        List<Author> bookAuthors = parseBookAuthors(authors);
        List<Genre> bookGenres = parseBookGenres(genres);

        Book book = bookRepository.save(new Book(0L, title, bookAuthors, bookGenres));
        return String.format(TEXT_NEW, book.getId(), book.getTitle());
    }

    @Override
    @Transactional
    public String deleteBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        bookRepository.deleteById(book.getId());
        return String.format(TEXT_DELETED, book.getId(), book.getTitle());
    }

    private List<Author> parseBookAuthors(String authorList) {
        return Arrays.stream(authorList.split(","))
                .map(l -> authorRepository.findByName(l).orElseThrow(() -> new ItemNotFoundException(String.format(TEXT_AUTHOR_NOT_FOUND, l))))
                .collect(Collectors.toList());
    }

    private List<Genre> parseBookGenres(String genresList) {
        return Arrays.stream(genresList.split(","))
                .map(l -> genreRepository.getByTitle(l).orElseThrow(() -> new ItemNotFoundException(String.format(TEXT_GENRE_NOT_FOUND, l))))
                .collect(Collectors.toList());
    }
}