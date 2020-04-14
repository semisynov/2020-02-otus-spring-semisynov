package ru.semisynov.otus.spring.homework05.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.semisynov.otus.spring.homework05.dao.GenreDao;
import ru.semisynov.otus.spring.homework05.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework05.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Класс GenreServiceImpl ")
class GenreServiceImplTest {

    @Configuration
    static class TestConfig {

        @Bean
        public GenreService genreService(GenreDao genreDao) {
            return new GenreServiceImpl(genreDao);
        }
    }

    private static final long EXPECTED_COUNT = 1L;
    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_NAME = "Test";
    private static final Genre EXPECTED_ENTITY = new Genre(EXPECTED_ID, EXPECTED_NAME);

    private static final String TEXT_EMPTY = "There are no genres in database";
    private static final String TEXT_COUNT = String.format("Genres in the database: %s", EXPECTED_COUNT);
    private static final String TEXT_BY_ID = String.format("Genre(id=%s, title=%s)", EXPECTED_ID, EXPECTED_NAME);
    private static final String TEXT_NEW = "New genre id: 2, name: Test2";

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("возвращает количество жанров")
    void shouldReturnGenresCount() {
        when(genreDao.count()).thenReturn(EXPECTED_COUNT);
        String result = genreService.getGenresCount();
        assertEquals(result, TEXT_COUNT);
    }

    @Test
    @DisplayName("возвращает количество жанров, когда нет в БД")
    void shouldReturnEmptyAuthorsCount() {
        when(genreDao.count()).thenReturn(0L);
        String result = genreService.getGenresCount();
        assertEquals(result, TEXT_EMPTY);
    }

    @Test
    @DisplayName("возвращает заданный жанр по его id")
    void shouldReturnExpectedGenreById() {
        when(genreDao.getById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
        String result = genreService.getGenreById(EXPECTED_ID);
        assertEquals(result, TEXT_BY_ID);
    }

    @Test
    @DisplayName("возвращает ошибку поиска жанра по её id")
    void shouldReturnItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> genreService.getGenreById(2L));
    }

    @Test
    @DisplayName("создает новый жанр")
    void shouldCreateGenre() {
        when(genreDao.insert(any())).thenReturn(2L);
        String result = genreService.createGenre("Test2");
        assertEquals(result, TEXT_NEW);
    }

    @Test
    @DisplayName("возвращает все жанры")
    void shouldReturnAllGenres() {
        List<Genre> genres = List.of(new Genre(0L, "Test1"), new Genre(0L, "Test2"), new Genre(0L, "Test3"));
        when(genreDao.getAll()).thenReturn(genres);

        String result = genreService.getAllGenres();
        assertEquals(result, genres.stream().map(Genre::toString).collect(Collectors.joining("\n")));
    }
}