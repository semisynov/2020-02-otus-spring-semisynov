package ru.semisynov.otus.spring.homework11.repositories;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Repository для работы с книгами ")
//@DataJpaTest
class BookRepositoryTest {
//
//    private static final int EXPECTED_BOOKS_COUNT = 3;
//    private static final long FIRST_ID = 1L;
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @DisplayName("возвращает общее количество всех книг")
//    @Test
//    void shouldReturnExpectedBooksCount() {
//        long count = bookRepository.count();
//        assertThat(count).isEqualTo(EXPECTED_BOOKS_COUNT);
//    }
//
//    @DisplayName("возвращает заданную книгу по её id")
//    @Test
//    void shouldReturnExpectedBookById() {
//        Optional<Book> optionalBook = bookRepository.findById(FIRST_ID);
//        Book expectedBook = entityManager.find(Book.class, FIRST_ID);
//        assertThat(optionalBook).isPresent().get()
//                .isEqualToComparingFieldByField(expectedBook);
//    }
//
//    @DisplayName("возвращает заданную книгу по её id dto")
//    @Test
//    void shouldReturnExpectedBookEntryById() {
//        Optional<Book> optionalBook = bookRepository.findById(FIRST_ID);
//        Book expectedBook = entityManager.find(Book.class, FIRST_ID);
//
//        assertThat(optionalBook).isPresent().get().matches(b -> b.getTitle().equals(expectedBook.getTitle()));
//    }
//
//    @DisplayName("возвращает список всех книг")
//    @Test
//    void shouldReturnExpectedBooksList() {
//        List<Book> books = bookRepository.findAll();
//        assertThat(books).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
//                .allMatch(b -> !b.getTitle().equals(""));
//    }
//
//    @DisplayName("добавляет книгу в БД")
//    @Test
//    void shouldInsertBook() {
//        List<Author> authors = Collections.emptyList();
//        List<Genre> genres = Collections.emptyList();
//
//        Book testBook = new Book(0L, "TestBook", authors, genres, Collections.emptyList());
//        bookRepository.save(testBook);
//
//        assertThat(testBook.getId()).isGreaterThan(0);
//
//        Book actualBook = entityManager.find(Book.class, testBook.getId());
//        assertThat(actualBook).isNotNull().matches(a -> !a.getTitle().isEmpty());
//    }
//
//    @DisplayName("удаляет книгу из БД")
//    @Test
//    void shouldDeleteBook() {
//        Book firstBook = entityManager.find(Book.class, FIRST_ID);
//        assertThat(firstBook).isNotNull();
//
//        bookRepository.delete(firstBook);
//        Book deletedBook = entityManager.find(Book.class, FIRST_ID);
//
//        assertThat(deletedBook).isNull();
//    }
}