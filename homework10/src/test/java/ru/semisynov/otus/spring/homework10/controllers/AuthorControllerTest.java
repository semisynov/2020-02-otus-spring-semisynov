package ru.semisynov.otus.spring.homework10.controllers;

import org.junit.jupiter.api.DisplayName;

//@WebMvcTest(AuthorController.class)
@DisplayName("Класс AuthorController ")
class AuthorControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AuthorService authorService;
//
//    private static final long EXPECTED_ID = 1L;
//    private static final String EXPECTED_NAME = "Test";
//    private static final Author EXPECTED_ENTITY = new Author(EXPECTED_ID, EXPECTED_NAME);
//
//    @Test
//    @SneakyThrows
//    @DisplayName("выводит view списка авторов")
//    public void shouldReturnAuthorsList() {
//        List<Author> authors = List.of(EXPECTED_ENTITY);
//        given(authorService.findAllAuthors()).willReturn(authors);
//
//        mockMvc.perform(get("/author"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(view().name("author/list"))
//                .andExpect(model().attribute("authors", authors))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("выводит view редактирования автора")
//    public void shouldReturnAuthorEdit() {
//        given(authorService.findAuthorById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(get("/author/edit?id=" + EXPECTED_ID))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(view().name("author/edit"))
//                .andExpect(model().attribute("author", EXPECTED_ENTITY))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("выводит view просмотра автора")
//    public void shouldReturnAuthorView() {
//        given(authorService.findAuthorById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(get("/author/view?id=" + EXPECTED_ID))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(view().name("author/view"))
//                .andExpect(model().attribute("author", EXPECTED_ENTITY))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("удаляет автора и переадресовывает в список")
//    public void shouldDeleteAuthor() throws Exception {
//        doNothing().when(authorService).deleteAuthorById(EXPECTED_ID);
//
//        mockMvc.perform(get("/author/delete?id=1"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/author"));
//    }
//
//    @Test
//    @DisplayName("не удаляет автора и переадресовывает на ошибку")
//    public void shouldNotDeleteAuthor() throws Exception {
//        doThrow(new DataReferenceException("Unable to delete the author %s there are links in the database"))
//                .when(authorService).deleteAuthorById(EXPECTED_ID);
//
//        mockMvc.perform(get("/author/delete?id=1"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(view().name("error/reference-exception"))
//                .andExpect(status().isOk());
//    }
}