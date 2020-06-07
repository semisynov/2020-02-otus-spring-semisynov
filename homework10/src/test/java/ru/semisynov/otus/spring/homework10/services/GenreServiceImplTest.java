package ru.semisynov.otus.spring.homework10.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.semisynov.otus.spring.homework10.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework10.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework10.model.Author;
import ru.semisynov.otus.spring.homework10.model.Book;
import ru.semisynov.otus.spring.homework10.model.Genre;
import ru.semisynov.otus.spring.homework10.repositories.BookRepository;
import ru.semisynov.otus.spring.homework10.repositories.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Класс GenreServiceImpl ")
class GenreServiceImplTest {

    @Configuration
    static class TestConfig {

        @Bean
        public GenreService genreService(GenreRepository genreRepository, BookRepository bookRepository) {
            return new GenreServiceImpl(genreRepository, bookRepository);
        }
    }

    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_TITLE = "Test";
    private static final Genre EXPECTED_ENTITY = new Genre(EXPECTED_ID, EXPECTED_TITLE);

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("возвращает заданный жанр по его id")
    void shouldReturnExpectedGenreById() {
        when(genreRepository.findById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
        Genre result = genreService.findGenreById(EXPECTED_ID);

        assertThat(result).isEqualToComparingFieldByField(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("возвращает ошибку поиска жанра по его id")
    void shouldReturnItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> genreService.findGenreById(2L));
    }

    @Test
    @DisplayName("сохраняет жанр в БД")
    void shouldCreateGenre() {
        Genre testGenre = new Genre(EXPECTED_TITLE);
        when(genreRepository.save(any())).thenReturn(testGenre);
        Genre result = genreService.saveGenre(testGenre);

        assertThat(result).isEqualToComparingFieldByField(testGenre);
    }

    @Test
    @DisplayName("обновляет жанр в БД")
    void shouldUpdateGenre() {
        Genre testGenre = new Genre(EXPECTED_TITLE);
        when(genreRepository.findById(any())).thenReturn(Optional.of(testGenre));
        when(genreRepository.save(any())).thenReturn(testGenre);
        Genre result = genreService.updateGenre(EXPECTED_ID, testGenre);

        assertThat(result).isEqualToComparingFieldByField(testGenre);
    }

    @Test
    @DisplayName("не обновляет жанр в БД")
    void shouldNotUpdateGenre() {
        Genre testGenre = new Genre(EXPECTED_TITLE);
        when(genreRepository.save(any())).thenReturn(testGenre);

        assertThrows(ItemNotFoundException.class, () -> genreService.updateGenre(10L, testGenre));
    }

    @Test
    @DisplayName("возвращает все жанры")
    void shouldReturnAllGenres() {
        List<Genre> genres = List.of(new Genre("Test1"), new Genre("Test2"), new Genre("Test3"));
        when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> result = genreService.findAllGenres();

        assertThat(result).isNotNull().hasSize(genres.size())
                .allMatch(a -> !a.getTitle().equals(""));
    }

    @Test
    @DisplayName("удаляет жанр из БД")
    void shouldDeleteGenre() {
        Genre testGenre = new Genre(5L, EXPECTED_TITLE);
        when(genreRepository.findById(5L)).thenReturn(Optional.of(testGenre));
        when(bookRepository.findByGenres(testGenre)).thenReturn(Collections.emptyList());
        doNothing().when(genreRepository).delete(testGenre);

        assertDoesNotThrow(() -> genreService.deleteGenreById(5L));
    }

    @Test
    @DisplayName("не удаляет жанр из БД")
    void shouldNotDeleteGenre() {
        Author testAuthor = new Author(10L, EXPECTED_TITLE);
        Genre testGenre = new Genre(10L, EXPECTED_TITLE);
        Book testBook = new Book("Test", List.of(testAuthor), List.of(testGenre));
        when(bookRepository.findByGenres(testGenre)).thenReturn(List.of(testBook));
        when(genreRepository.findById(10L)).thenReturn(Optional.of(testGenre));

        doNothing().when(genreRepository).delete(testGenre);

        assertThrows(DataReferenceException.class, () -> genreService.deleteGenreById(10L));
    }
}