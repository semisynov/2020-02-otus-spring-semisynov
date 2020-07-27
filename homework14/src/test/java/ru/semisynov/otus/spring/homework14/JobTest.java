package ru.semisynov.otus.spring.homework14;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@SpringBatchTest
public class JobTest {

    private static final int EXPECTED_AUTHORS_COUNT = 5;
    private static final int EXPECTED_GENRES_COUNT = 5;
    private static final int EXPECTED_BOOKS_COUNT = 5;
    private static final int EXPECTED_COMMENTS_COUNT = 5;
    private static final int EXPECTED_BOOK_AUTHOR_COUNT = 5;
    private static final int EXPECTED_BOOK_GENRE_COUNT = 5;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    @Ignore
    void jobExecutedTest() throws Exception {
        assertNotNull(jobLauncherTestUtils.getJob());
        jobLauncherTestUtils.launchJob();

        Integer countAuthors = jdbcTemplate.queryForObject("select count(*) from authors", Integer.class);
        assertEquals(EXPECTED_AUTHORS_COUNT, countAuthors);
        Integer countGenres = jdbcTemplate.queryForObject("select count(*) from genres", Integer.class);
        assertEquals(EXPECTED_GENRES_COUNT, countGenres);
        Integer countBooks = jdbcTemplate.queryForObject("select count(*) from books", Integer.class);
        assertEquals(EXPECTED_BOOKS_COUNT, countBooks);
        Integer countComments = jdbcTemplate.queryForObject("select count(*) from comments", Integer.class);
        assertEquals(EXPECTED_COMMENTS_COUNT, countComments);
        Integer countBookAuthor = jdbcTemplate.queryForObject("select count(*) from book_author", Integer.class);
        assertEquals(EXPECTED_BOOK_AUTHOR_COUNT, countBookAuthor);
        Integer countBookGenre = jdbcTemplate.queryForObject("select count(*) from book_genre", Integer.class);
        assertEquals(EXPECTED_BOOK_GENRE_COUNT, countBookGenre);
    }

    @Test
    void stepExecutionTest() {
        assertEquals(BatchStatus.COMPLETED, jobLauncherTestUtils.launchStep("authorMigrationStep").getStatus());
        assertEquals(BatchStatus.COMPLETED, jobLauncherTestUtils.launchStep("genreMigrationStep").getStatus());
        assertEquals(BatchStatus.COMPLETED, jobLauncherTestUtils.launchStep("bookMigrationStep").getStatus());
        assertEquals(BatchStatus.COMPLETED, jobLauncherTestUtils.launchStep("commentMigrationStep").getStatus());
        assertEquals(BatchStatus.COMPLETED, jobLauncherTestUtils.launchStep("bookAuthorMigrationStep").getStatus());
        assertEquals(BatchStatus.COMPLETED, jobLauncherTestUtils.launchStep("bookGenreMigrationStep").getStatus());
    }
}
