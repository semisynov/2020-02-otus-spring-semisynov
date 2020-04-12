package ru.semisynov.otus.spring.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;


@DisplayName("Dao для работы с книгами ")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
//
//    public static final int EXPECTED_BOOKS_COUNT = 1;
//
//    @Autowired
//    private BookDao bookDao;
//
//    @DisplayName("возвращает заданную книгу по её id")
//    @Test
//    void shouldReturnExpectedBookById() {
//        Book book = bookDao.getById(1L);
//        assertEquals(book.getAuthorId(), BookDaoJdbcTest.EXPECTED_BOOKS_COUNT);
//    }
//
//   // @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
//    @DisplayName("добавляет книгу в БД")
//    @Test
//    void shouldInsertBook() {
//        Book expected = new Book(0L, "Test", 0L, 0L);
//        bookDao.insert(expected);
//        Book actual = bookDao.getById(expected.getId());
//        assertThat(expected).isEqualToComparingFieldByField(actual);
//    }
}