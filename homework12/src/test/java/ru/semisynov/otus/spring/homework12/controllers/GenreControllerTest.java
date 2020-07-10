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
import ru.semisynov.otus.spring.homework12.model.Genre;
import ru.semisynov.otus.spring.homework12.repositories.UserRepository;
import ru.semisynov.otus.spring.homework12.security.SecurityConfiguration;
import ru.semisynov.otus.spring.homework12.security.SecurityUserDetailsService;
import ru.semisynov.otus.spring.homework12.services.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({GenreController.class, SecurityConfiguration.class, SecurityUserDetailsService.class, ApplicationConfig.class})
@DisplayName("Класс GenreController ")
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @MockBean
    private UserRepository userRepository;

    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_NAME = "Test";
    private static final Genre EXPECTED_ENTITY = new Genre(EXPECTED_ID, EXPECTED_NAME);

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("выводит view списка жанров")
    public void shouldReturnGenresList() {
        List<Genre> genres = List.of(EXPECTED_ENTITY);
        given(genreService.findAllGenres()).willReturn(genres);

        mockMvc.perform(get("/genre"))
                .andDo(print())
                .andExpect(view().name("genre/list"))
                .andExpect(model().attribute("genres", genres))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("не выводит view списка жанров и перекидывает на логин")
    public void shouldNotReturnGenresList() {
        List<Genre> genres = List.of(EXPECTED_ENTITY);
        given(genreService.findAllGenres()).willReturn(genres);

        mockMvc.perform(get("/genre"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("выводит view редактирования жанров")
    public void shouldReturnGenreEdit() {
        given(genreService.findGenreById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);

        mockMvc.perform(get("/genre/edit?id=" + EXPECTED_ID))
                .andDo(print())
                .andExpect(view().name("genre/edit"))
                .andExpect(model().attribute("genre", EXPECTED_ENTITY))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("выводит view просмотра жанров")
    public void shouldReturnGenreView() {
        given(genreService.findGenreById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);

        mockMvc.perform(get("/genre/view?id=" + EXPECTED_ID))
                .andDo(print())
                .andExpect(view().name("genre/view"))
                .andExpect(model().attribute("genre", EXPECTED_ENTITY))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("удаляет жанр и переадресовывает в список")
    public void shouldDeleteGenre() throws Exception {
        doNothing().when(genreService).deleteGenreById(EXPECTED_ID);

        mockMvc.perform(get("/genre/delete?id=1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genre"));
    }

    @Test
    @WithMockUser
    @DisplayName("не удаляет жанр и переадресовывает на ошибку")
    public void shouldNotDeleteGenre() throws Exception {
        doThrow(new DataReferenceException("Unable to delete the genre %s there are links in the database"))
                .when(genreService).deleteGenreById(EXPECTED_ID);

        mockMvc.perform(get("/genre/delete?id=1"))
                .andDo(print())
                .andExpect(view().name("error/reference-exception"))
                .andExpect(status().isOk());
    }
}