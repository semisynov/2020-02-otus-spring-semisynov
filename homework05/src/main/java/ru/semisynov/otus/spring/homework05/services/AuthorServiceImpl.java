package ru.semisynov.otus.spring.homework05.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.semisynov.otus.spring.homework05.dao.AuthorDao;
import ru.semisynov.otus.spring.homework05.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework05.model.Author;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    private static final String TEXT_EMPTY = "There are no authors in database";
    private static final String TEXT_NOT_FOUND = "Author not found";
    private static final String TEXT_COUNT = "Authors in the database: %s";
    private static final String TEXT_NEW = "New author id: %s, name: %s";
    private static final String TEXT_DELETED = "Successfully deleted author id: %s, name: %s";

    @Override
    public String getAuthorsCount() {
        long count = authorDao.count();
        return count != 0 ? String.format(TEXT_COUNT, count) : TEXT_EMPTY;
    }

    @Override
    public String getAuthorById(long id) {
        Author author = authorDao.getById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        return author.toString();
    }

    @Override
    public String getAllAuthors() {
        List<Author> authors = authorDao.getAll();
        String authorsResult;
        if (authors.isEmpty()) {
            authorsResult = TEXT_EMPTY;
        } else {
            authorsResult = authors.stream().map(Author::toString).collect(Collectors.joining("\n"));
        }
        return authorsResult;
    }

    @Override
    public String createAuthor(String name) {
        long newId = authorDao.insert(new Author(0L, name));
        return String.format(TEXT_NEW, newId, name);
    }

    @Override
    public String deleteAuthorById(long id) {
        Author author = authorDao.getById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        authorDao.deleteById(author.getId());
        return String.format(TEXT_DELETED, author.getId(), author.getName());
    }
}
