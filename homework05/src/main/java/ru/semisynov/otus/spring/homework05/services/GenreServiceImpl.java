package ru.semisynov.otus.spring.homework05.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.semisynov.otus.spring.homework05.dao.GenreDao;
import ru.semisynov.otus.spring.homework05.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework05.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    private static final String TEXT_EMPTY = "There are no genres in database";
    private static final String TEXT_NOT_FOUND = "Genre not found";
    private static final String TEXT_COUNT = "Genres in the database: %s";
    private static final String TEXT_NEW = "New genre id: %s, name: %s";
    private static final String TEXT_DELETED = "Successfully deleted genre id: %s, title: %s";

    @Override
    public String getGenresCount() {
        long count = genreDao.count();
        return count != 0 ? String.format(TEXT_COUNT, count) : TEXT_EMPTY;
    }

    @Override
    public String getGenreById(long id) {
        Genre genre = genreDao.getById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        return genre.toString();
    }

    @Override
    public String getAllGenres() {
        List<Genre> genres = genreDao.getAll();
        String genresResult;
        if (genres.isEmpty()) {
            genresResult = TEXT_EMPTY;
        } else {
            genresResult = genres.stream().map(Genre::toString).collect(Collectors.joining("\n"));
        }
        return genresResult;
    }

    @Override
    public String createGenre(String title) {
        long newId = genreDao.insert(new Genre(0L, title));
        return String.format(TEXT_NEW, newId, title);
    }

    @Override
    public String deleteGenreById(long id) {
        Genre genre = genreDao.getById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        genreDao.deleteById(genre.getId());
        return String.format(TEXT_DELETED, genre.getId(), genre.getTitle());
    }
}
