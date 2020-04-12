package ru.semisynov.otus.spring.homework05.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework05.model.Author;
import ru.semisynov.otus.spring.homework05.model.Book;
import ru.semisynov.otus.spring.homework05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long count() {
        return jdbc.queryForObject("select count(*) from books", Collections.emptyMap(), Integer.class);
    }

    @Override
    public Optional<Book> getById(long id) {
        Optional<Book> book;

        try {
            book = Optional.ofNullable(jdbc.queryForObject("select * " +
                            "from books where book_id = :id",
                    Map.of("id", id), new BookDaoJdbc.BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            book = Optional.empty();
        }
        return book;
    }

//    @Override
//    public List<Author> getAll() {
//        List<Author> authors = jdbc.query("select * from authors", new AuthorDaoJdbc.AuthorMapper());
//        return authors;
//    }
//
//    @Override
//    public long insert(Author author) {
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("name", author.getName());
//        KeyHolder kh = new GeneratedKeyHolder();
//        jdbc.update("insert into authors (name) values (:name)",
//                params, kh);
//        return kh.getKey().longValue();
//    }
//
//    @Override
//    public void deleteById(long id) {
//        jdbc.update("delete from authors where author_id = :id",
//                Map.of("id", id));
//    }
//
//private final long id;
//    private final String title;
//    private final List<Author> authors;
//    private final List<Genre> genres;
    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long bookId = resultSet.getLong("book_id");
            String title = resultSet.getString("title");
            List<Author> authors = new ArrayList<>();
            List<Genre> genres = new ArrayList<>();
            return new Book(bookId, title,  authors, genres);
        }
    }
}
