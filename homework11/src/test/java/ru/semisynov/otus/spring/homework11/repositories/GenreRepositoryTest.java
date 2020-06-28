package ru.semisynov.otus.spring.homework11.repositories;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Repository для работы с жанрами книг ")
//@DataJpaTest
class GenreRepositoryTest {
//
//    private static final int EXPECTED_GENRES_COUNT = 3;
//    private static final long FIRST_ID = 1L;
//    private static final String EXPECTED_GENRE_TITLE_1 = "AnyGenre1";
//
//    @Autowired
//    private GenreRepository genreRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @DisplayName("возвращает общее количество всех жанров")
//    @Test
//    void shouldReturnExpectedGenresCount() {
//        long count = genreRepository.count();
//        assertThat(count).isEqualTo(EXPECTED_GENRES_COUNT);
//    }
//
//    @DisplayName("возвращает заданный жанр по его id")
//    @Test
//    void shouldReturnExpectedGenreById() {
//        Optional<Genre> optionalGenre = genreRepository.findById(1L);
//        Genre expectedGenre = entityManager.find(Genre.class, FIRST_ID);
//        assertThat(optionalGenre).isPresent().get()
//                .isEqualToComparingFieldByField(expectedGenre);
//    }
//
//    @DisplayName("возвращает список всех жанров ")
//    @Test
//    void shouldReturnExpectedGenresList() {
//        List<Genre> genres = genreRepository.findAll();
//        assertThat(genres).isNotNull().hasSize(EXPECTED_GENRES_COUNT)
//                .allMatch(g -> !g.getTitle().equals(""));
//    }
//
//    @DisplayName("добавляет жанр в БД")
//    @Test
//    void shouldInsertGenre() {
//        Genre testGenre = new Genre(0L, "TestGenre");
//        genreRepository.save(testGenre);
//
//        assertThat(testGenre.getId()).isGreaterThan(0);
//
//        Genre actualGenre = entityManager.find(Genre.class, testGenre.getId());
//        assertThat(actualGenre).isNotNull().matches(g -> !g.getTitle().isEmpty());
//    }
//
//    @DisplayName("удаляет жанр из БД")
//    @Test
//    void shouldDeleteGenre() {
//        Genre firstGenre = entityManager.find(Genre.class, FIRST_ID);
//        assertThat(firstGenre).isNotNull();
//
//        genreRepository.delete(firstGenre);
//        Genre deletedGenre = entityManager.find(Genre.class, FIRST_ID);
//
//        assertThat(deletedGenre).isNull();
//    }
//
//    @DisplayName("возвращает заданного жанр по его названию")
//    @Test
//    void shouldReturnExpectedGenreByTitle() {
//        Optional<Genre> optionalGenre = genreRepository.findByTitleIgnoreCase(EXPECTED_GENRE_TITLE_1);
//        Genre expectedGenre = entityManager.find(Genre.class, FIRST_ID);
//        assertThat(optionalGenre).isPresent().get()
//                .isEqualToComparingFieldByField(expectedGenre);
//    }
}