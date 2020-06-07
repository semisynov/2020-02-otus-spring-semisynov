package ru.semisynov.otus.spring.homework10.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import ru.semisynov.otus.spring.homework10.config.ApplicationConfig;
import ru.semisynov.otus.spring.homework10.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework10.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework10.model.Genre;
import ru.semisynov.otus.spring.homework10.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({GenreController.class, ApplicationConfig.class})
@DisplayName("Класс GenreController ")
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_TITLE = "Test";
    private static final Genre EXPECTED_ENTITY = new Genre(EXPECTED_ID, EXPECTED_TITLE);

    @Test
    @SneakyThrows
    @DisplayName("выводит списк всех жанров")
    public void shouldReturnGenresList() {
        List<Genre> genres = List.of(EXPECTED_ENTITY);
        given(genreService.findAllGenres()).willReturn(genres);

        mockMvc.perform(get("/genre")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is(EXPECTED_TITLE)))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @SneakyThrows
    @DisplayName("отдает жанр по id")
    public void shouldReturnGenreById() {
        given(genreService.findGenreById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);

        mockMvc.perform(get("/genre/" + EXPECTED_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(EXPECTED_TITLE)));
    }

    @Test
    @SneakyThrows
    @DisplayName("не отдает жанр, нет в БД")
    public void shouldNotReturnGenre() {
        doThrow(new ItemNotFoundException("Genre not found"))
                .when(genreService).findGenreById(EXPECTED_ID);

        assertThrows(NestedServletException.class, () -> mockMvc.perform(get("/genre/10")));
    }

    @Test
    @SneakyThrows
    @DisplayName("создает новый жанр")
    public void shouldCreateGenre() {
        given(genreService.saveGenre(any(Genre.class))).willReturn(EXPECTED_ENTITY);

        mockMvc.perform(post("/genre/")
                .content("{\"title\": \"Test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(EXPECTED_TITLE)));
    }

    @Test
    @SneakyThrows
    @DisplayName("обновляет жанр в БД")
    public void shouldUpdateGenre() {
        given(genreService.updateGenre(any(Long.class), any(Genre.class))).willReturn(EXPECTED_ENTITY);

        mockMvc.perform(put("/genre/" + EXPECTED_ID)
                .content("{\"title\": \"Test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(EXPECTED_TITLE)));
    }

    @Test
    @SneakyThrows
    @DisplayName("удаляет жанр из БД")
    public void shouldDeleteGenre() {
        doNothing().when(genreService).deleteGenreById(EXPECTED_ID);

        mockMvc.perform(delete("/genre/" + EXPECTED_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("не удаляет жанр из БД")
    public void shouldNotDeleteGenre() {
        doThrow(new DataReferenceException("Unable to delete the genre %s there are links in the database"))
                .when(genreService).deleteGenreById(EXPECTED_ID);

        assertThrows(NestedServletException.class, () -> mockMvc.perform(delete("/genre/1")));
    }
}