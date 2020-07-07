package ru.semisynov.otus.spring.homework11.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.semisynov.otus.spring.homework11.config.ApplicationTestConfig;
import ru.semisynov.otus.spring.homework11.dto.CommentDto;
import ru.semisynov.otus.spring.homework11.model.Author;
import ru.semisynov.otus.spring.homework11.model.Book;
import ru.semisynov.otus.spring.homework11.model.Comment;
import ru.semisynov.otus.spring.homework11.model.Genre;
import ru.semisynov.otus.spring.homework11.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework11.repositories.BookRepository;
import ru.semisynov.otus.spring.homework11.repositories.CommentRepository;
import ru.semisynov.otus.spring.homework11.repositories.GenreRepository;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(controllers = CommentController.class)
@Import({ApplicationTestConfig.class})
@DisplayName("Класс CommentController ")
class CommentControllerTest {

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
    private static final Book BOOK = new Book(EXPECTED_ID, EXPECTED_TITLE, Collections.singletonList(AUTHOR), Collections.singletonList(GENRE));
    private static final Comment EXPECTED_ENTITY = new Comment(BOOK, "Text");

    @Test
    @DisplayName("выводит списк всех комментариев по книге")
    public void shouldReturnBookCommentList() {
        Mockito.when(commentRepository.findAllByBookId(EXPECTED_ID)).thenReturn(Flux.fromIterable(Collections.singletonList(EXPECTED_ENTITY)));

        webClient.get().uri("/comment/{id}", EXPECTED_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .hasSize(1);
        Mockito.verify(commentRepository)
                .findAllByBookId(EXPECTED_ID);
    }

    @Test
    @DisplayName("создает новый комментарий")
    public void shouldCreateComment() {
        Mockito.when(bookRepository.findById(EXPECTED_ID)).thenReturn(Mono.just(BOOK));
        Mockito.when(commentRepository.save(any())).thenReturn(Mono.just(EXPECTED_ENTITY));

        webClient.post().uri(uriBuilder -> uriBuilder
                .path("/comment/{bookId}")
                .queryParam("text", "text")
                .build(EXPECTED_ID))
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(commentRepository, Mockito.times(1)).save(any());
    }
}