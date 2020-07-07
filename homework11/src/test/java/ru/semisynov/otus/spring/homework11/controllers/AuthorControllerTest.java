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
import ru.semisynov.otus.spring.homework11.dto.AuthorDto;
import ru.semisynov.otus.spring.homework11.model.Author;
import ru.semisynov.otus.spring.homework11.model.Book;
import ru.semisynov.otus.spring.homework11.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework11.repositories.BookRepository;
import ru.semisynov.otus.spring.homework11.repositories.CommentRepository;
import ru.semisynov.otus.spring.homework11.repositories.GenreRepository;

import java.util.Collections;

@WebFluxTest(controllers = AuthorController.class)
@Import({ApplicationTestConfig.class})
@DisplayName("Класс AuthorController ")
class AuthorControllerTest {

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
    private static final String EXPECTED_NAME = "Test";
    private static final Author EXPECTED_ENTITY = new Author(EXPECTED_ID, EXPECTED_NAME);

    @Test
    @DisplayName("выводит списк всех авторов")
    public void shouldReturnAuthorsList() {
        Mockito.when(authorRepository.findAll()).thenReturn(Flux.fromIterable(Collections.singletonList(EXPECTED_ENTITY)));

        webClient.get().uri("/author")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .hasSize(1);
        Mockito.verify(authorRepository)
                .findAll();
    }

    @Test
    @DisplayName("отдает автора по id")
    public void shouldReturnAuthorById() {
        Mockito.when(authorRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));

        webClient.get().uri("/author/{id}", EXPECTED_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .hasSize(1);
        Mockito.verify(authorRepository)
                .findById(EXPECTED_ID);
    }

    @Test
    @DisplayName("не отдает автора, нет в БД")
    public void shouldNotReturnAuthor() {
        webClient.get().uri("/author/{id}", "1")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("создает нового автора")
    public void shouldCreateAuthor() {
        Mockito.when(authorRepository.save(EXPECTED_ENTITY)).thenReturn(Mono.just(EXPECTED_ENTITY));

        webClient.post().uri("/author")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(convertToDto(EXPECTED_ENTITY)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .hasSize(1);
        Mockito.verify(authorRepository)
                .save(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("обновляет автора в БД")
    public void shouldUpdateAuthor() {
        Mockito.when(authorRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));
        Mockito.when(authorRepository.save(EXPECTED_ENTITY)).thenReturn(Mono.just(EXPECTED_ENTITY));
        EXPECTED_ENTITY.setName("Test");

        webClient.put().uri("/author/{id}", EXPECTED_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(convertToDto(EXPECTED_ENTITY)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .hasSize(1);
        Mockito.verify(authorRepository)
                .save(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("удаляет автора из БД")
    public void shouldDeleteAuthor() {
        Mockito.when(authorRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));
        Mockito.when(bookRepository.findByAuthors(EXPECTED_ENTITY)).thenReturn(Flux.empty());
        Mockito.when(authorRepository.delete(EXPECTED_ENTITY)).thenReturn(Mono.empty());

        webClient.delete().uri("/author/{id}", EXPECTED_ID)
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(authorRepository, Mockito.times(1)).findById(EXPECTED_ID);
        Mockito.verify(authorRepository)
                .delete(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("не удаляет автора из БД")
    public void shouldNotDeleteAuthor() {
        Mockito.when(authorRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));
        Mockito.when(bookRepository.findByAuthors(EXPECTED_ENTITY)).thenReturn(Flux.fromIterable(Collections.singletonList(new Book())));
        Mockito.when(authorRepository.delete(EXPECTED_ENTITY)).thenReturn(Mono.empty());

        webClient.delete().uri("/author/{id}", EXPECTED_ID)
                .exchange()
                .expectStatus().is5xxServerError();
        Mockito.verify(authorRepository, Mockito.times(1)).findById(EXPECTED_ID);
        Mockito.verify(authorRepository, Mockito.times(0)).delete(EXPECTED_ENTITY);
    }

    private AuthorDto convertToDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }
}