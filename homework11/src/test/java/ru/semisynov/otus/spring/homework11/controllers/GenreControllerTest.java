package ru.semisynov.otus.spring.homework11.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.semisynov.otus.spring.homework11.config.ApplicationTestConfig;
import ru.semisynov.otus.spring.homework11.dto.GenreDto;
import ru.semisynov.otus.spring.homework11.model.Book;
import ru.semisynov.otus.spring.homework11.model.Genre;
import ru.semisynov.otus.spring.homework11.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework11.repositories.BookRepository;
import ru.semisynov.otus.spring.homework11.repositories.CommentRepository;
import ru.semisynov.otus.spring.homework11.repositories.GenreRepository;

import java.util.Collections;

@WebFluxTest(controllers = GenreController.class)
@Import({ApplicationTestConfig.class})
@DisplayName("Класс GenreController ")
class GenreControllerTest {

    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private GenreRepository genreRepository;
    @Autowired
    private WebTestClient webClient;
    @Autowired
    private ModelMapper modelMapper;

    private static final String EXPECTED_ID = "12345";
    private static final String EXPECTED_TITLE = "Test";
    private static final Genre EXPECTED_ENTITY = new Genre(EXPECTED_ID, EXPECTED_TITLE);

    @Test
    @DisplayName("выводит списк всех жанров")
    public void shouldReturnGenresList() {
        Mockito.when(genreRepository.findAll()).thenReturn(Flux.fromIterable(Collections.singletonList(EXPECTED_ENTITY)));

        webClient.get().uri("/genre")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class)
                .hasSize(1);
        Mockito.verify(genreRepository)
                .findAll();
    }

    @Test
    @DisplayName("отдает жанр по id")
    public void shouldReturnGenreById() {
        Mockito.when(genreRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));

        webClient.get().uri("/genre/{id}", EXPECTED_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class)
                .hasSize(1);
        Mockito.verify(genreRepository)
                .findById(EXPECTED_ID);
    }

    @Test
    @DisplayName("не отдает жанр, нет в БД")
    public void shouldNotReturnGenre() {
        webClient.get().uri("/genre/{id}", "1")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("создает нового жанр")
    public void shouldCreateGenre() {
        Mockito.when(genreRepository.save(EXPECTED_ENTITY)).thenReturn(Mono.just(EXPECTED_ENTITY));

        webClient.post().uri("/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(convertToDto(EXPECTED_ENTITY)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class)
                .hasSize(1);
        Mockito.verify(genreRepository)
                .save(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("обновляет жанр в БД")
    public void shouldUpdateGenre() {
        Mockito.when(genreRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));
        Mockito.when(genreRepository.save(EXPECTED_ENTITY)).thenReturn(Mono.just(EXPECTED_ENTITY));
        EXPECTED_ENTITY.setTitle("Test");

        webClient.put().uri("/genre/{id}", EXPECTED_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(convertToDto(EXPECTED_ENTITY)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class)
                .hasSize(1);
        Mockito.verify(genreRepository)
                .save(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("удаляет жанр из БД")
    public void shouldDeleteGenre() {
        Mockito.when(genreRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));
        Mockito.when(bookRepository.findByGenres(EXPECTED_ENTITY)).thenReturn(Flux.empty());
        Mockito.when(genreRepository.delete(EXPECTED_ENTITY)).thenReturn(Mono.empty());

        webClient.delete().uri("/genre/{id}", EXPECTED_ID)
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(genreRepository, Mockito.times(1)).findById(EXPECTED_ID);
        Mockito.verify(genreRepository)
                .delete(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("не удаляет жанр из БД")
    public void shouldNotDeleteGenre() {
        Mockito.when(genreRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));
        Mockito.when(bookRepository.findByGenres(EXPECTED_ENTITY)).thenReturn(Flux.fromIterable(Collections.singletonList(new Book())));
        Mockito.when(genreRepository.delete(EXPECTED_ENTITY)).thenReturn(Mono.empty());

        webClient.delete().uri("/genre/{id}", EXPECTED_ID)
                .exchange()
                .expectStatus().is5xxServerError();
        Mockito.verify(genreRepository, Mockito.times(1)).findById(EXPECTED_ID);
        Mockito.verify(genreRepository, Mockito.times(0)).delete(EXPECTED_ENTITY);
    }

    private GenreDto convertToDto(Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }
}