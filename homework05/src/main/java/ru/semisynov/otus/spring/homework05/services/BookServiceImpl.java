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

import java.util.List;

@Service
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

    @Override
    public String getBooksCount() {
        long count = bookDao.count();
        return count != 0 ? String.format(TEXT_COUNT, count) : TEXT_EMPTY;
    }

    @Override
    public String getBookById(long id) {
        Book book = bookDao.getById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));

        List<Author> bookAuthors = authorDao.getByBookId(book.getId());
        bookAuthors.forEach(book::addAuthor);

        List<Genre> bookGenres = genreDao.getByBookId(book.getId());
        bookGenres.forEach(book::addGenre);
        return book.toString();
    }
//
//    @Override
//    public String getAllAuthors() {
//        List<Author> authors = authorDao.getAll();
//        String authorsResult;
//        if (authors.isEmpty()) {
//            authorsResult = TEXT_EMPTY;
//        } else {
//            authorsResult = authors.stream().map(Author::toString).collect(Collectors.joining("\n"));
//        }
//        return authorsResult;
//    }
//
//    @Override
//    public String createAuthor(String name) {
//        long newId = authorDao.insert(new Author(0L, name));
//        return String.format(TEXT_NEW, newId, name);
//    }
//
//    @Override
//    public String deleteAuthorById(long id) {
//        Author author = authorDao.getById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
//        authorDao.deleteById(author.getId());
//        return String.format(TEXT_DELETED, author.getId(), author.getName());
//    }
}
