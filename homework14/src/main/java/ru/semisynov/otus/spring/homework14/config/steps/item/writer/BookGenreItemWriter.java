package ru.semisynov.otus.spring.homework14.config.steps.item.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.semisynov.otus.spring.homework14.model.Book;
import ru.semisynov.otus.spring.homework14.model.Genre;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class BookGenreItemWriter implements ItemWriter<Book> {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookGenreItemWriter(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void write(List<? extends Book> books) {
        for (Book book : books) {
            for (Genre genre : book.getGenres()) {
                Map<String, String> parameterSource =
                        Map.of("book_id", book.getId(), "genre_id", genre.getId());
                jdbcTemplate.update(
                        "INSERT INTO book_genre (book_id, genre_id) VALUES (:book_id, :genre_id)",
                        parameterSource);
            }
        }
    }
}
