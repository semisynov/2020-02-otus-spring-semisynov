package ru.semisynov.otus.spring.homework09.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework09.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework09.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework09.model.Book;
import ru.semisynov.otus.spring.homework09.model.Genre;
import ru.semisynov.otus.spring.homework09.repositories.BookRepository;
import ru.semisynov.otus.spring.homework09.repositories.GenreRepository;

import java.util.List;

@Slf4j
@Service("genreService")
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    private static final String TEXT_NOT_FOUND = "Genre not found";

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findGenreById(long id) {
        return genreRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
    }

    @Override
    @Transactional
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void deleteGenreById(long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        List<Book> books = bookRepository.findByGenres(genre);
        if (books.size() != 0) {
            throw new DataReferenceException(String.format("Unable to delete the genre %s there are links in the database", id));
        }
        genreRepository.delete(genre);
    }
}