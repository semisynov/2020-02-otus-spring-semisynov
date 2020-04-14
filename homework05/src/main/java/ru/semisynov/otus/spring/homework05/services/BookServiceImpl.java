package ru.semisynov.otus.spring.homework05.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.semisynov.otus.spring.homework05.dao.AuthorDao;
import ru.semisynov.otus.spring.homework05.dao.BookDao;
import ru.semisynov.otus.spring.homework05.dao.GenreDao;
import ru.semisynov.otus.spring.homework05.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework05.model.Author;
import ru.semisynov.otus.spring.homework05.model.Book;
import ru.semisynov.otus.spring.homework05.model.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("bookService")
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    private static final String TEXT_EMPTY = "There are no books in database";
    private static final String TEXT_NOT_FOUND = "Book not found";
    private static final String TEXT_COUNT = "Books in the database: %s";
    private static final String TEXT_NEW = "New book id: %s, name: %s";
    private static final String TEXT_DELETED = "Successfully deleted book id: %s, name: %s";
    private static final String TEXT_AUTHOR_NOT_FOUND = "Author %s not found";
    private static final String TEXT_GENRE_NOT_FOUND = "Genre %s not found";

    @Override
    public String getBooksCount() {
        long count = bookDao.count();
        return count != 0 ? String.format(TEXT_COUNT, count) : TEXT_EMPTY;
    }

    @Override
    public String getBookById(long id) {
        Book book = bookDao.getById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        setAdditionalInformation(book);

        return book.toString();
    }

    @Override
    public String getAllBooks() {
        List<Book> books = bookDao.getAll();
        books.forEach(this::setAdditionalInformation);

        String booksResult;
        if (books.isEmpty()) {
            booksResult = TEXT_EMPTY;
        } else {
            booksResult = books.stream().map(Book::toString).collect(Collectors.joining("\n"));
        }
        return booksResult;
    }

    private void setAdditionalInformation(Book book) {
        List<Author> bookAuthors = authorDao.getByBookId(book.getId());
        bookAuthors.forEach(book::addAuthor);

        List<Genre> bookGenres = genreDao.getByBookId(book.getId());
        bookGenres.forEach(book::addGenre);
    }

    private List<Author> parseBookAuthors(String authorList) {
        List<Author> authors = Arrays.stream(authorList.split(","))
                .map(l -> authorDao.getByName(l).orElseThrow(() -> new ItemNotFoundException(String.format(TEXT_AUTHOR_NOT_FOUND, l))))
                .collect(Collectors.toList());
        return authors;
    }

    private List<Genre> parseBookGenres(String genresList) {
        List<Genre> genres = Arrays.stream(genresList.split(","))
                .map(l -> genreDao.getByTitle(l).orElseThrow(() -> new ItemNotFoundException(String.format(TEXT_GENRE_NOT_FOUND, l))))
                .collect(Collectors.toList());
        return genres;
    }

    @Override
    public String createBook(String title, String authors, String genres) {
        List<Author> bookAuthors = parseBookAuthors(authors);
        List<Genre> bookGenres = parseBookGenres(genres);

        long newId = bookDao.insert(new Book(0L, title, bookAuthors, bookGenres));
        return String.format(TEXT_NEW, newId, title);
    }

    @Override
    public String deleteBookById(long id) {
        Book book = bookDao.getById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        bookDao.deleteById(book.getId());
        return String.format(TEXT_DELETED, book.getId(), book.getTitle());
    }
}