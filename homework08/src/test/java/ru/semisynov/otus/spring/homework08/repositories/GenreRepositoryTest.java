package ru.semisynov.otus.spring.homework08.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.semisynov.otus.spring.homework08.config.ApplicationTestConfig;
import ru.semisynov.otus.spring.homework08.config.MongoConfig;
import ru.semisynov.otus.spring.homework08.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework08.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework08.model.Genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Repository для работы с жанрами книг ")
@DataMongoTest
@Import({ApplicationTestConfig.class, MongoConfig.class})
@ComponentScan("ru.semisynov.otus.spring.homework08.events")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GenreRepositoryTest {

    private static final int EXPECTED_GENRES_COUNT = 10;
    private static final String GENRE_TITLE_PREFIX = "ТестЖанр";
    private static final String TEXT_NOT_FOUND = "Genre not found";

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("возвращает общее количество всех жанров")
    @Test
    void shouldReturnExpectedGenresCount() {
        long count = genreRepository.count();
        assertThat(count).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("возвращает заданный жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        List<Genre> genres = mongoTemplate.findAll(Genre.class);
        Genre genre = genres.get(0);

        Optional<Genre> optionalGenre = genreRepository.findById(genre.getId());

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(genre.getId()));
        Genre expectedGenre = mongoTemplate.findOne(query, Genre.class);

        assertThat(optionalGenre).isPresent().get()
                .isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName("возвращает список всех жанров")
    @Test
    void shouldReturnExpectedGenresList() {
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_GENRES_COUNT)
                .allMatch(g -> !g.getTitle().equals(""));
    }

    @DisplayName("добавляет жанр в БД")
    @Test
    void shouldInsertGenre() {
        String genreTitle = GENRE_TITLE_PREFIX + "10";
        Genre genre = new Genre(genreTitle);
        genreRepository.save(genre);

        assertThat(genre.getId()).isNotEmpty();

        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(genreTitle));
        Genre expectedGenre = mongoTemplate.findOne(query, Genre.class);

        assertThat(genre).isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName("возвращает заданный жанр по его названию")
    @Test
    void shouldReturnExpectedGenreByTitle() {
        String genreTitle = GENRE_TITLE_PREFIX + "1";

        Optional<Genre> optionalGenre = genreRepository.findByTitleIgnoreCase(genreTitle.toLowerCase());

        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(genreTitle));
        Genre expectedGenre = mongoTemplate.findOne(query, Genre.class);

        assertThat(optionalGenre).isPresent().get()
                .isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName("удаляет жанр из БД")
    @Test
    void shouldDeleteGenre() {
        String genreWithOutBookName = GENRE_TITLE_PREFIX + "9";

        Optional<Genre> optionalGenre = genreRepository.findByTitleIgnoreCase(genreWithOutBookName.toLowerCase());
        String genreId = optionalGenre.orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND)).getId();

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(genreId));
        Genre firstGenre = mongoTemplate.findOne(query, Genre.class);

        assertThat(firstGenre).isNotNull();

        genreRepository.delete(firstGenre);
        Genre deletedGenre = mongoTemplate.findOne(query, Genre.class);

        assertThat(deletedGenre).isNull();
    }

    @DisplayName("не удаляет жанр из БД, есть связи")
    @Test
    void shouldNotDeleteGenreWithBook() {
        String genreWithOutBookName = GENRE_TITLE_PREFIX + "0";

        Optional<Genre> optionalGenre = genreRepository.findByTitleIgnoreCase(genreWithOutBookName.toLowerCase());
        String genreId = optionalGenre.orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND)).getId();

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(genreId));
        Genre firstGenre = mongoTemplate.findOne(query, Genre.class);

        assertThat(firstGenre).isNotNull();

        assertThrows(DataReferenceException.class, () -> genreRepository.delete(firstGenre));
    }
}