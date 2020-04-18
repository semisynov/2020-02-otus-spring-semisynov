package ru.semisynov.otus.spring.homework06.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.semisynov.otus.spring.homework06.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework06.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Repository для работы с жанрами книг ")
@DataJpaTest
@Import(GenreRepositoryImpl.class)
class GenreRepositoryImplTest {

    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final int EXPECTED_GENRES_COUNT = 3;
    private static final long FIRST_ID = 1L;
    private static final String EXPECTED_GENRE_TITLE_1 = "AnyGenre1";

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("возвращает общее количество всех жанров")
    @Test
    void shouldReturnExpectedGenresCount() {
        long count = genreRepository.count();
        assertThat(count).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("возвращает заданный жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Optional<Genre> optionalGenre = genreRepository.findById(1L);
        Genre expectedGenre = entityManager.find(Genre.class, FIRST_ID);
        assertThat(optionalGenre).isPresent().get()
                .isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName("возвращает список всех жанров ")
    @Test
    void shouldReturnExpectedGenresList() {
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_GENRES_COUNT)
                .allMatch(g -> !g.getTitle().equals(""));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("добавляет жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre testGenre = new Genre(0L, "TestGenre");
        genreRepository.save(testGenre);

        assertThat(testGenre.getId()).isGreaterThan(0);

        Genre actualGenre = entityManager.find(Genre.class, testGenre.getId());
        assertThat(actualGenre).isNotNull().matches(g -> !g.getTitle().isEmpty());
    }

    @DisplayName("удаляет жанр из БД")
    @Test
    void shouldDeleteGenre() {
        Genre firstGenre = entityManager.find(Genre.class, FIRST_ID);
        assertThat(firstGenre).isNotNull();
        entityManager.detach(firstGenre);

        genreRepository.deleteById(FIRST_ID);
        Genre deletedGenre = entityManager.find(Genre.class, FIRST_ID);

        assertThat(deletedGenre).isNull();
    }

    @DisplayName("не удаляет автора из БД есть связь")
    @Test
    void shouldNotDeleteGenre() {
        assertThrows(DataReferenceException.class, () -> genreRepository.deleteById(2L));
    }

    @DisplayName("возвращает заданного жанр по его названию")
    @Test
    void shouldReturnExpectedGenreByTitle() {
        Optional<Genre> optionalGenre = genreRepository.getByTitle(EXPECTED_GENRE_TITLE_1);
        Genre expectedGenre = entityManager.find(Genre.class, FIRST_ID);
        assertThat(optionalGenre).isPresent().get()
                .isEqualToComparingFieldByField(expectedGenre);
    }
}