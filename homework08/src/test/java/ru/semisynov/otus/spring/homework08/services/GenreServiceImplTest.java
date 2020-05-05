package ru.semisynov.otus.spring.homework08.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.semisynov.otus.spring.homework08.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework08.model.Genre;
import ru.semisynov.otus.spring.homework08.repositories.GenreRepository;

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
        public GenreService genreService(GenreRepository genreRepository) {
            return new GenreServiceImpl(genreRepository);
        }
    }

    private static final long EXPECTED_COUNT = 1L;
    private static final String EXPECTED_ID = "12345";
    private static final String EXPECTED_TITLE = "Test";
    private static final Genre EXPECTED_ENTITY = new Genre(EXPECTED_ID, EXPECTED_TITLE);

    private static final String TEXT_EMPTY = "There are no genres in database";
    private static final String TEXT_COUNT = String.format("Genres in the database: %s", EXPECTED_COUNT);
    private static final String TEXT_BY_ID = String.format("Genre(id=%s, title=%s)", EXPECTED_ID, EXPECTED_TITLE);
    private static final String TEXT_NEW = "New genre id: %s, name: %s";

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("возвращает количество жанров")
    void shouldReturnGenresCount() {
        when(genreRepository.count()).thenReturn(EXPECTED_COUNT);
        String result = genreService.getGenresCount();
        assertEquals(result, TEXT_COUNT);
    }

    @Test
    @DisplayName("возвращает количество жанров, когда нет в БД")
    void shouldReturnEmptyAuthorsCount() {
        when(genreRepository.count()).thenReturn(0L);
        String result = genreService.getGenresCount();
        assertEquals(result, TEXT_EMPTY);
    }

//    @Test
//    @DisplayName("возвращает заданный жанр по его id")
//    void shouldReturnExpectedGenreById() {
//        when(genreRepository.findById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
//        String result = genreService.getGenreById(EXPECTED_ID);
//        assertEquals(result, TEXT_BY_ID);
//    }
//
//    @Test
//    @DisplayName("возвращает ошибку поиска жанра по его id")
//    void shouldReturnItemNotFoundException() {
//        assertThrows(ItemNotFoundException.class, () -> genreService.getGenreById(2L));
//    }
//
//    @Test
//    @DisplayName("создает новый жанр")
//    void shouldCreateGenre() {
//        Genre testGenre = new Genre(10L, EXPECTED_TITLE);
//        when(genreRepository.save(any())).thenReturn(testGenre);
//        String result = genreService.addGenre(EXPECTED_TITLE);
//        assertEquals(result, String.format(TEXT_NEW, 10L, EXPECTED_TITLE));
//    }
//
//    @Test
//    @DisplayName("возвращает все жанры")
//    void shouldReturnAllGenres() {
//        List<Genre> genres = List.of(new Genre(0L, "Test1"), new Genre(0L, "Test2"), new Genre(0L, "Test3"));
//        when(genreRepository.findAll()).thenReturn(genres);
//
//        String result = genreService.getAllGenres();
//        assertEquals(result, genres.stream().map(Genre::toString).collect(Collectors.joining("\n")));
//    }
}