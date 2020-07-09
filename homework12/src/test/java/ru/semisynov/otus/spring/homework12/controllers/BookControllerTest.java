package ru.semisynov.otus.spring.homework12.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.semisynov.otus.spring.homework12.config.ApplicationConfig;
import ru.semisynov.otus.spring.homework12.dto.BookEntry;
import ru.semisynov.otus.spring.homework12.model.Author;
import ru.semisynov.otus.spring.homework12.model.Book;
import ru.semisynov.otus.spring.homework12.model.Genre;
import ru.semisynov.otus.spring.homework12.repositories.UserRepository;
import ru.semisynov.otus.spring.homework12.security.SecurityConfiguration;
import ru.semisynov.otus.spring.homework12.security.SecurityUserDetailsService;
import ru.semisynov.otus.spring.homework12.services.AuthorService;
import ru.semisynov.otus.spring.homework12.services.BookService;
import ru.semisynov.otus.spring.homework12.services.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({BookController.class, SecurityConfiguration.class, SecurityUserDetailsService.class, ApplicationConfig.class})
@DisplayName("Класс BookController ")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private UserRepository userRepository;

    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_NAME = "Test";
    private static final Author AUTHOR = new Author(EXPECTED_NAME);
    private static final Genre GENRE = new Genre(EXPECTED_NAME);
    private Book testBook;
    private BookEntry testBookEntry;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(EXPECTED_ID);
        testBook.setAuthors(List.of(AUTHOR));
        testBook.setGenres(List.of(GENRE));

        testBookEntry = new BookEntry() {
            @Override
            public long getId() {
                return EXPECTED_ID;
            }

            @Override
            public String getTitle() {
                return EXPECTED_NAME;
            }

            @Override
            public String getAuthorsInfo() {
                return AUTHOR.getName();
            }

            @Override
            public String getGenresInfo() {
                return GENRE.getTitle();
            }
        };
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void shouldReturnBooksList() {
        List<BookEntry> books = List.of(testBookEntry);
        given(bookService.findAllBooks()).willReturn(books);

        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(view().name("book/list"))
                .andExpect(model().attribute("books", books))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("не выводит view списка книг и перекидывает на логин")
    public void shouldNotReturnBooksList() {
        List<BookEntry> books = List.of(testBookEntry);
        given(bookService.findAllBooks()).willReturn(books);

        mockMvc.perform(get("/book"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void shouldReturnBookEdit() {
        given(bookService.findBookById(EXPECTED_ID)).willReturn(testBook);

        mockMvc.perform(get("/book/edit?id=" + EXPECTED_ID))
                .andDo(print())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("book", testBook))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void shouldReturnBookView() {
        given(bookService.findBookById(EXPECTED_ID)).willReturn(testBook);

        mockMvc.perform(get("/book/view?id=" + EXPECTED_ID))
                .andDo(print())
                .andExpect(view().name("book/view"))
                .andExpect(model().attribute("book", testBook))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void shouldDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBookById(EXPECTED_ID);

        mockMvc.perform(get("/book/delete?id=1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
    }
}