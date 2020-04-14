package ru.semisynov.otus.spring.homework05.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework05.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework05.model.Author;
import ru.semisynov.otus.spring.homework05.model.Book;
import ru.semisynov.otus.spring.homework05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("bookDao")
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
            book = Optional.ofNullable(jdbc.queryForObject("select book_id, title " +
                            "from books where book_id = :id",
                    Map.of("id", id), new BookDaoJdbc.BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            book = Optional.empty();
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = jdbc.query("select book_id, title from books", new BookDaoJdbc.BookMapper());
        return books;
    }

    @Override
    public long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into books (title) values (:title)",
                params, kh);
        long newId = kh.getKey() != null ? kh.getKey().longValue() : 0;
        if (newId != 0) {
            try {
                book.getAuthors().forEach(a -> insertBookAuthors(newId, a.getId()));
                book.getGenres().forEach(g -> insertBookGenres(newId, g.getId()));
            } catch (Exception e) {
                deleteBook(newId);
                deleteBookReferences(newId);
                throw new DataReferenceException("Error adding link for book");
            }
        }
        return newId;
    }

    @Override
    public void deleteById(long id) {
        deleteBook(id);
        deleteBookReferences(id);
    }

    private void deleteBook(long id) {
        jdbc.update("delete from books where book_id = :id",
                Map.of("id", id));
    }

    private void deleteBookReferences(long id) {
        jdbc.update("delete from book_author where book_id = :id",
                Map.of("id", id));
        jdbc.update("delete from book_genre where book_id = :id",
                Map.of("id", id));
    }

    private void insertBookAuthors(long bookId, long authorId) {
        jdbc.update("insert into book_author values (:bookId, :authorId)",
                Map.of("bookId", bookId, "authorId", authorId));
    }

    private void insertBookGenres(long bookId, long genreId) {
        jdbc.update("insert into book_genre values (:bookId, :genreId)",
                Map.of("bookId", bookId, "genreId", genreId));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long bookId = resultSet.getLong("book_id");
            String title = resultSet.getString("title");
            List<Author> authors = new ArrayList<>();
            List<Genre> genres = new ArrayList<>();
            return new Book(bookId, title, authors, genres);
        }
    }
}