package ru.semisynov.otus.spring.homework08.services;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.semisynov.otus.spring.homework08.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework08.model.Author;
import ru.semisynov.otus.spring.homework08.repositories.AuthorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("authorService")
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private static final String TEXT_EMPTY = "There are no authors in database";
    private static final String TEXT_NOT_FOUND = "Author not found";
    private static final String TEXT_COUNT = "Authors in the database: %s";
    private static final String TEXT_NEW = "New author id: %s, name: %s";
    private static final String TEXT_DELETED = "Successfully deleted author id: %s, name: %s";

    @Override
    public String getAuthorsCount() {
        long count = authorRepository.count();
        return count != 0 ? String.format(TEXT_COUNT, count) : TEXT_EMPTY;
    }

    @Override
    public String findAuthorById(String id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        return author.toString();
    }

    @Override
    public String findAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        String authorsResult;
        if (authors.isEmpty()) {
            authorsResult = TEXT_EMPTY;
        } else {
            authorsResult = authors.stream().map(Author::toString).collect(Collectors.joining("\n"));
        }
        return authorsResult;
    }

    @Override
    @Transactional
    public String addAuthor(String name) {
        Author author = authorRepository.save(new Author(name));
        return String.format(TEXT_NEW, author.getId(), author.getName());
    }

    @Override
    @Transactional
    public String deleteAuthorById(String id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        authorRepository.delete(author);
        return String.format(TEXT_DELETED, author.getId(), author.getName());
    }
}