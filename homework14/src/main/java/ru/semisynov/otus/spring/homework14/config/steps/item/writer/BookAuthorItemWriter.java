package ru.semisynov.otus.spring.homework14.config.steps.item.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.semisynov.otus.spring.homework14.model.Author;
import ru.semisynov.otus.spring.homework14.model.Book;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class BookAuthorItemWriter implements ItemWriter<Book> {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookAuthorItemWriter(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void write(List<? extends Book> books) {
        for (Book book : books) {
            for (Author author : book.getAuthors()) {
                Map<String, String> parameterSource =
                        Map.of("book_id", book.getId(), "author_id", author.getId());
                jdbcTemplate.update(
                        "INSERT INTO book_author (book_id, author_id) VALUES (:book_id, :author_id)",
                        parameterSource);
            }
        }
    }
}
