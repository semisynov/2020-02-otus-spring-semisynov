package ru.semisynov.otus.spring.homework11.repositories;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Repository для работы с комментариями для книг ")
//@DataJpaTest
class CommentRepositoryTest {
//
//    private static final int EXPECTED_COMMENTS_COUNT = 3;
//    private static final long FIRST_ID = 1L;
//    private static final String EXPECTED_TEXT = "AnyText1";
//    private static final long FIRST_BOOK_ID = 1L;
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @DisplayName("возвращает общее количество всех авторов")
//    @Test
//    void shouldReturnExpectedCommentsCount() {
//        long count = commentRepository.count();
//        assertThat(count).isEqualTo(EXPECTED_COMMENTS_COUNT);
//    }
//
//    @DisplayName("возвращает заданного комментарий по его id")
//    @Test
//    void shouldReturnExpectedCommentById() {
//        Optional<Comment> optionalComment = commentRepository.findById(FIRST_ID);
//        Comment expectedComment = entityManager.find(Comment.class, FIRST_ID);
//        assertThat(optionalComment).isPresent().get()
//                .isEqualToComparingFieldByField(expectedComment);
//    }
//
//    @DisplayName("возвращает список всех комментариев")
//    @Test
//    void shouldReturnExpectedCommentsList() {
//        List<Comment> comments = commentRepository.findAll();
//        assertThat(comments).isNotNull().hasSize(EXPECTED_COMMENTS_COUNT)
//                .allMatch(a -> !a.getText().equals(""));
//    }
//
//    @DisplayName("добавляет комментарий для книги в БД")
//    @Test
//    void shouldInsertComment() {
//        Book testBook = entityManager.find(Book.class, FIRST_BOOK_ID);
//        Comment testComment = new Comment(0L, LocalDateTime.now(), EXPECTED_TEXT, testBook);
//        commentRepository.save(testComment);
//
//        assertThat(testComment.getId()).isGreaterThan(0);
//
//        Comment actualComment = entityManager.find(Comment.class, testComment.getId());
//        assertThat(actualComment).isNotNull().matches(c -> c.getText().equals(EXPECTED_TEXT));
//    }
//
//    @DisplayName("удаляет комментарий из БД")
//    @Test
//    void shouldDeleteComment() {
//        Comment firstComment = entityManager.find(Comment.class, FIRST_ID);
//        assertThat(firstComment).isNotNull();
//
//        commentRepository.delete(firstComment);
//        Comment deletedComment = entityManager.find(Comment.class, FIRST_ID);
//
//        assertThat(deletedComment).isNull();
//    }
}