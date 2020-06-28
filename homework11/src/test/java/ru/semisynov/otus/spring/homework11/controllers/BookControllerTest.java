package ru.semisynov.otus.spring.homework11.controllers;

//@WebMvcTest({BookController.class, ApplicationConfig.class})
//@DisplayName("Класс BookController ")
class BookControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private BookService bookService;
//
//    @MockBean
//    private CommentService commentService;
//
//    private static final long EXPECTED_ID = 1L;
//    private static final String EXPECTED_TITLE = "Test";
//    private static final Author AUTHOR = new Author("TestAuthor");
//    private static final Genre GENRE = new Genre("TestGenre");
//    private static final Book EXPECTED_ENTITY = new Book(EXPECTED_ID, EXPECTED_TITLE, List.of(AUTHOR), List.of(GENRE), Collections.emptyList());
//
//    @Test
//    @SneakyThrows
//    @DisplayName("выводит списк всех книг")
//    public void shouldReturnBooksList() {
//        List<Book> books = List.of(EXPECTED_ENTITY);
//        given(bookService.findAllBooks()).willReturn(books);
//
//        mockMvc.perform(get("/book")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].title", is(EXPECTED_TITLE)))
//                .andExpect(jsonPath("$[0].authors", hasSize(1)))
//                .andExpect(jsonPath("$[0].genres", hasSize(1)))
//                .andExpect(jsonPath("$[0].comments", hasSize(0)))
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("отдает книгу по id")
//    public void shouldReturnBookById() {
//        given(bookService.findBookById(EXPECTED_ID)).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(get("/book/" + EXPECTED_ID)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.title", is(EXPECTED_TITLE)))
//                .andExpect(jsonPath("$.authors", hasSize(1)))
//                .andExpect(jsonPath("$.genres", hasSize(1)))
//                .andExpect(jsonPath("$.comments", hasSize(0)));
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("не отдает книгу, нет в БД")
//    public void shouldNotReturnBook() {
//        doThrow(new ItemNotFoundException("Book not found"))
//                .when(bookService).findBookById(EXPECTED_ID);
//
//        assertThrows(NestedServletException.class, () -> mockMvc.perform(get("/book/10")));
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("создает новую книгу")
//    public void shouldCreateBook() {
//        given(bookService.saveBook(any(Book.class))).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(post("/book/")
//                .content("{\"title\": \"Test\", \"authors\": [{\"id\": 1, \"name\": \"TestAuthor\"}], \"genres\": [{\"id\": 1,\"title\": \"TestGenre\"}]}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.title", is(EXPECTED_TITLE)));
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("обновляет книгу в БД")
//    public void shouldUpdateBook() {
//        given(bookService.updateBook(any(Long.class), any(Book.class))).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(put("/book/" + EXPECTED_ID)
//                .content("{\"title\": \"Test\", \"authors\": [{\"id\": 1, \"name\": \"TestAuthor\"}], \"genres\": [{\"id\": 1,\"title\": \"TestGenre\"}]}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.title", is(EXPECTED_TITLE)));
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("удаляет книгу из БД")
//    public void shouldDeleteBook() {
//        doNothing().when(bookService).deleteBookById(EXPECTED_ID);
//
//        mockMvc.perform(delete("/book/" + EXPECTED_ID))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @SneakyThrows
//    @DisplayName("создает новый комментарий и возвращает обновленную dto")
//    public void shouldCreateNewComment() {
//        EXPECTED_ENTITY.setComments(List.of(new Comment(EXPECTED_ENTITY, "NewComment")));
//        given(bookService.addBookComment(any(Long.class), any(String.class))).willReturn(EXPECTED_ENTITY);
//
//        mockMvc.perform(post("/book/" + EXPECTED_ID + "/comment")
//                .param("text", "NewComment")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.title", is(EXPECTED_TITLE)))
//                .andExpect(jsonPath("$.comments", hasSize(1)));
//    }
}