package ru.semisynov.otus.spring.homework12.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.semisynov.otus.spring.homework12.config.ApplicationConfig;
import ru.semisynov.otus.spring.homework12.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework12.model.Author;
import ru.semisynov.otus.spring.homework12.repositories.UserRepository;
import ru.semisynov.otus.spring.homework12.security.SecurityConfiguration;
import ru.semisynov.otus.spring.homework12.security.SecurityUserDetailsService;
import ru.semisynov.otus.spring.homework12.services.AuthorService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({AuthorController.class, SecurityConfiguration.class, SecurityUserDetailsService.class, ApplicationConfig.class})
@DisplayName("Класс AuthorController ")
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private UserRepository userRepository;

    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_NAME = "Test";
    private static final Author EXPECTED_ENTITY = new Author(EXPECTED_ID, EXPECTED_NAME);

    @Test
    @SneakyThrows
    @DisplayName("выводит view списка авторов")
    @WithMockUser(value = "user", roles = {"USER"})
    public void shouldReturnAuthorsList() {
        List<Author> authors = List.of(EXPECTED_ENTITY);
        given(authorService.findAllAuthors()).willReturn(authors);

        mockMvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("author/list"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("выводит view редактирования автора")
    public void shouldReturnAuthorEdit() {
        given(authorService.findAuthorById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);

        mockMvc.perform(get("/author/edit?id=" + EXPECTED_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("author/edit"))
                .andExpect(model().attribute("author", EXPECTED_ENTITY))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("выводит view просмотра автора")
    public void shouldReturnAuthorView() {
        given(authorService.findAuthorById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);

        mockMvc.perform(get("/author/view?id=" + EXPECTED_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("author/view"))
                .andExpect(model().attribute("author", EXPECTED_ENTITY))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("удаляет автора и переадресовывает в список")
    public void shouldDeleteAuthor() throws Exception {
        doNothing().when(authorService).deleteAuthorById(EXPECTED_ID);

        mockMvc.perform(get("/author/delete?id=1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));
    }

    @Test
    @DisplayName("не удаляет автора и переадресовывает на ошибку")
    public void shouldNotDeleteAuthor() throws Exception {
        doThrow(new DataReferenceException("Unable to delete the author %s there are links in the database"))
                .when(authorService).deleteAuthorById(EXPECTED_ID);

        mockMvc.perform(get("/author/delete?id=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("error/reference-exception"))
                .andExpect(status().isOk());
    }
}