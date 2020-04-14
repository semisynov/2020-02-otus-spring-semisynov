package ru.semisynov.otus.spring.homework05.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework05.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework05.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("authorDao")
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long count() {
        return jdbc.queryForObject("select count(*) from authors", Collections.emptyMap(), Integer.class);
    }

    @Override
    public Optional<Author> getById(long id) {
        Optional<Author> author;
        try {
            author = Optional.ofNullable(jdbc.queryForObject("select author_id, name from authors where author_id = :id",
                    Map.of("id", id), new AuthorMapper()));
        } catch (EmptyResultDataAccessException e) {
            author = Optional.empty();
        }
        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = jdbc.query("select author_id, name from authors", new AuthorMapper());
        return authors;
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into authors (name) values (:name)",
                params, kh);
        return kh.getKey() != null ? kh.getKey().longValue() : 0L;
    }

    @Override
    public void deleteById(long id) {
        try {
            jdbc.update("delete from authors where author_id = :id",
                    Map.of("id", id));
        } catch (DataIntegrityViolationException e) {
            throw new DataReferenceException(String.format("Unable to delete the author %s there are links in the database", id));
        }
    }

    @Override
    public List<Author> getByBookId(long id) {
        List<Author> authors = jdbc.query("select a.author_id, a.name from authors a " +
                "inner join book_author ba on ba.author_id = a.author_id " +
                "where ba.book_id = :id", Map.of("id", id), new AuthorMapper());
        return authors;
    }

    @Override
    public Optional<Author> getByName(String name) {
        Optional<Author> author;
        try {
            author = Optional.ofNullable(jdbc.queryForObject("select author_id, name from authors where upper(name) = upper(:name)",
                    Map.of("name", name), new AuthorMapper()));
        } catch (EmptyResultDataAccessException e) {
            author = Optional.empty();
        }
        return author;
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long authorId = resultSet.getLong("author_id");
            String name = resultSet.getString("name");
            return new Author(authorId, name);
        }
    }
}