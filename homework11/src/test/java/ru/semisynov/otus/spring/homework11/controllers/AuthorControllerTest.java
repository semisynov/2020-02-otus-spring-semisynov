package ru.semisynov.otus.spring.homework11.controllers;

//@WebMvcTest({AuthorController.class, ApplicationConfig.class})
//@DisplayName("Класс AuthorController ")
class AuthorControllerTest {
//
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
//    @DisplayName("выводит списк всех авторов")
//    public void shouldReturnAuthorsList() {
//        List<Author> authors = List.of(EXPECTED_ENTITY);
//        given(authorService.findAllAuthors()).willReturn(authors);
//
//        mockMvc.perform(get("/author")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].name", is(EXPECTED_NAME)))
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("отдает автора по id")
//    public void shouldReturnAuthorById() {
//        given(authorService.findAuthorById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(get("/author/" + EXPECTED_ID)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.name", is(EXPECTED_NAME)));
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("не отдает автора, нет в БД")
//    public void shouldNotReturnAuthor() {
//        doThrow(new ItemNotFoundException("Author not found"))
//                .when(authorService).findAuthorById(EXPECTED_ID);
//
//        assertThrows(NestedServletException.class, () -> mockMvc.perform(get("/author/10")));
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("создает нового автора")
//    public void shouldCreateAuthor() {
//        given(authorService.saveAuthor(any(Author.class))).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(post("/author/")
//                .content("{\"name\": \"Test\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.name", is(EXPECTED_NAME)));
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("обновляет автора в БД")
//    public void shouldUpdateAuthor() {
//        given(authorService.updateAuthor(any(Long.class), any(Author.class))).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(put("/author/" + EXPECTED_ID)
//                .content("{\"name\": \"Test\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.name", is(EXPECTED_NAME)));
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("удаляет автора из БД")
//    public void shouldDeleteAuthor() {
//        doNothing().when(authorService).deleteAuthorById(EXPECTED_ID);
//
//        mockMvc.perform(delete("/author/" + EXPECTED_ID))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("не удаляет автора из БД")
//    public void shouldNotDeleteAuthor() {
//        doThrow(new DataReferenceException("Unable to delete the author %s there are links in the database"))
//                .when(authorService).deleteAuthorById(EXPECTED_ID);
//
//        assertThrows(NestedServletException.class, () -> mockMvc.perform(delete("/author/1")));
//    }
}