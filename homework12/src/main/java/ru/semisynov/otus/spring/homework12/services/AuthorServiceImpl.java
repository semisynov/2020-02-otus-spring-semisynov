package ru.semisynov.otus.spring.homework12.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework12.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework12.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework12.model.Author;
import ru.semisynov.otus.spring.homework12.model.Book;
import ru.semisynov.otus.spring.homework12.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework12.repositories.BookRepository;

import java.util.List;

@Slf4j
@Service("authorService")
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    private static final String TEXT_NOT_FOUND = "Author not found";

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author findAuthorById(long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
    }

    @Override
    @Transactional
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthorById(long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        List<Book> books = bookRepository.findByAuthors(author);
        if (books.size() != 0) {
            throw new DataReferenceException(String.format("Unable to delete the author %s there are links in the database", id));
        }
        authorRepository.delete(author);
    }
}