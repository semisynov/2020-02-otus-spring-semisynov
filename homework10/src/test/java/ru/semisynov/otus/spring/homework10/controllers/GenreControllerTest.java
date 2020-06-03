package ru.semisynov.otus.spring.homework10.controllers;

import org.junit.jupiter.api.DisplayName;

//@WebMvcTest(GenreController.class)
@DisplayName("Класс GenreController ")
class GenreControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private GenreService genreService;
//
//    private static final long EXPECTED_ID = 1L;
//    private static final String EXPECTED_NAME = "Test";
//    private static final Genre EXPECTED_ENTITY = new Genre(EXPECTED_ID, EXPECTED_NAME);
//
//    @Test
//    @SneakyThrows
//    @DisplayName("выводит view списка жанров")
//    public void shouldReturnGenresList() {
//        List<Genre> genres = List.of(EXPECTED_ENTITY);
//        given(genreService.findAllGenres()).willReturn(genres);
//
//        mockMvc.perform(get("/genre"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(view().name("genre/list"))
//                .andExpect(model().attribute("genres", genres))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("выводит view редактирования жанров")
//    public void shouldReturnGenreEdit() {
//        given(genreService.findGenreById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(get("/genre/edit?id=" + EXPECTED_ID))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(view().name("genre/edit"))
//                .andExpect(model().attribute("genre", EXPECTED_ENTITY))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("выводит view просмотра жанров")
//    public void shouldReturnGenreView() {
//        given(genreService.findGenreById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(get("/genre/view?id=" + EXPECTED_ID))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(view().name("genre/view"))
//                .andExpect(model().attribute("genre", EXPECTED_ENTITY))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("удаляет жанр и переадресовывает в список")
//    public void shouldDeleteGenre() throws Exception {
//        doNothing().when(genreService).deleteGenreById(EXPECTED_ID);
//
//        mockMvc.perform(get("/genre/delete?id=1"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/genre"));
//    }
//
//    @Test
//    @DisplayName("не удаляет жанр и переадресовывает на ошибку")
//    public void shouldNotDeleteGenre() throws Exception {
//        doThrow(new DataReferenceException("Unable to delete the genre %s there are links in the database"))
//                .when(genreService).deleteGenreById(EXPECTED_ID);
//
//        mockMvc.perform(get("/genre/delete?id=1"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(view().name("error/reference-exception"))
//                .andExpect(status().isOk());
//    }
}