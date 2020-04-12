package ru.semisynov.otus.spring.homework05.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long count() {
        return jdbc.queryForObject("select count(*) from genres", Collections.emptyMap(), Integer.class);
    }

    @Override
    public Optional<Genre> getById(long id) {
        Optional<Genre> genre;
        try {
            genre = Optional.ofNullable(jdbc.queryForObject("select * from genres where genre_id = :id",
                    Map.of("id", id), new GenreDaoJdbc.GenreMapper()));
        } catch (EmptyResultDataAccessException e) {
            genre = Optional.empty();
        }
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genres", new GenreMapper());
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", genre.getTitle());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into genres (title) values (:title)",
                params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from genres where genre_id = :id",
                Map.of("id", id));
    }

    @Override
    public List<Genre> getByBookId(long id) {
        List<Genre> genres = jdbc.query("select * from genres g " +
                "inner join book_genre bg on bg.genre_id = g.genre_id " +
                "where bg.book_id = :id", Map.of("id", id), new GenreDaoJdbc.GenreMapper());
        return genres;
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long genreId = resultSet.getLong("genre_id");
            String title = resultSet.getString("title");
            return new Genre(genreId, title);
        }
    }
}
