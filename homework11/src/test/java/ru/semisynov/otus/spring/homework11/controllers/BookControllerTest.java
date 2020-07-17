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
import ru.semisynov.otus.spring.homework11.dto.BookDto;
import ru.semisynov.otus.spring.homework11.model.Author;
import ru.semisynov.otus.spring.homework11.model.Book;
import ru.semisynov.otus.spring.homework11.model.Genre;
import ru.semisynov.otus.spring.homework11.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework11.repositories.BookRepository;
import ru.semisynov.otus.spring.homework11.repositories.CommentRepository;
import ru.semisynov.otus.spring.homework11.repositories.GenreRepository;

import java.util.Collections;

@WebFluxTest(controllers = BookController.class)
@Import({ApplicationTestConfig.class})
@DisplayName("Класс BookController ")
class BookControllerTest {

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
    private static final Author AUTHOR = new Author("TestAuthor");
    private static final Genre GENRE = new Genre("TestGenre");
    private static final Book EXPECTED_ENTITY = new Book(EXPECTED_ID, EXPECTED_TITLE, Collections.singletonList(AUTHOR), Collections.singletonList(GENRE));

    @Test
    @DisplayName("выводит списк всех книг")
    public void shouldReturnBooksList() {
        Mockito.when(bookRepository.findAll()).thenReturn(Flux.fromIterable(Collections.singletonList(EXPECTED_ENTITY)));

        webClient.get().uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(1);
        Mockito.verify(bookRepository)
                .findAll();
    }

    @Test
    @DisplayName("отдает книгу по id")
    public void shouldReturnBookById() {
        Mockito.when(bookRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));

        webClient.get().uri("/book/{id}", EXPECTED_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(1);
        Mockito.verify(bookRepository)
                .findById(EXPECTED_ID);
    }

    @Test
    @DisplayName("не отдает книгу, нет в БД")
    public void shouldNotReturnBook() {
        webClient.get().uri("/book/{id}", "1")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("создает новую книгу")
    public void shouldCreateBook() {
        Mockito.when(bookRepository.save(EXPECTED_ENTITY)).thenReturn(Mono.just(EXPECTED_ENTITY));

        webClient.post().uri("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(convertToDto(EXPECTED_ENTITY)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(1);
        Mockito.verify(bookRepository)
                .save(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("обновляет книгу в БД")
    public void shouldUpdateBook() {
        Mockito.when(bookRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));
        Mockito.when(bookRepository.save(EXPECTED_ENTITY)).thenReturn(Mono.just(EXPECTED_ENTITY));
        EXPECTED_ENTITY.setTitle("Test");

        webClient.put().uri("/book/{id}", EXPECTED_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(convertToDto(EXPECTED_ENTITY)))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(1);
        Mockito.verify(bookRepository)
                .save(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("удаляет книгу из БД")
    public void shouldDeleteBook() {
        Mockito.when(bookRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(EXPECTED_ENTITY));
        Mockito.when(bookRepository.delete(EXPECTED_ENTITY)).thenReturn(Mono.empty());
        Mockito.when(commentRepository.deleteAllByBook(EXPECTED_ENTITY)).thenReturn(Mono.empty());

        webClient.delete().uri("/book/{id}", EXPECTED_ID)
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(bookRepository, Mockito.times(1)).findById(EXPECTED_ID);
        Mockito.verify(bookRepository)
                .delete(EXPECTED_ENTITY);
    }

    private BookDto convertToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }
}