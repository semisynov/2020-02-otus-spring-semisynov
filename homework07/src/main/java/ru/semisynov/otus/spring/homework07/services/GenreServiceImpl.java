package ru.semisynov.otus.spring.homework07.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semisynov.otus.spring.homework07.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework07.model.Genre;
import ru.semisynov.otus.spring.homework07.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("genreService")
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private static final String TEXT_EMPTY = "There are no genres in database";
    private static final String TEXT_NOT_FOUND = "Genre not found";
    private static final String TEXT_COUNT = "Genres in the database: %s";
    private static final String TEXT_NEW = "New genre id: %s, name: %s";
    private static final String TEXT_DELETED = "Successfully deleted genre id: %s, title: %s";

    @Override
    public String getGenresCount() {
        long count = genreRepository.count();
        return count != 0 ? String.format(TEXT_COUNT, count) : TEXT_EMPTY;
    }

    @Override
    public String getGenreById(long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        return genre.toString();
    }

    @Override
    public String getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        String genresResult;
        if (genres.isEmpty()) {
            genresResult = TEXT_EMPTY;
        } else {
            genresResult = genres.stream().map(Genre::toString).collect(Collectors.joining("\n"));
        }
        return genresResult;
    }

    @Override
    @Transactional
    public String addGenre(String title) {
        Genre genre = genreRepository.save(new Genre(0L, title));
        return String.format(TEXT_NEW, genre.getId(), genre.getTitle());
    }

    @Override
    @Transactional
    public String deleteGenreById(long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        genreRepository.delete(genre);
        return String.format(TEXT_DELETED, genre.getId(), genre.getTitle());
    }
}