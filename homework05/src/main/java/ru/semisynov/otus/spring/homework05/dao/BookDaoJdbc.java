package ru.semisynov.otus.spring.homework05.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework05.model.Book;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        //Map<String, Object> params = Collections.singletonMap("id", id);
        //Map<String, Object> params = new HashMap<>();
        return jdbc.queryForObject("select count(*) from books", Collections.emptyMap(), Integer.class);
    }

    @Override
    public void insert(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("name", book.getName());
        jdbc.update("insert into books (id, name) values (:id, :name)", params);
    }

//    @Override
//    public Book getById(long id) {
//        Map<String, Object> params = Collections.singletonMap("id", id);
//        return jdbc.queryForObject(
//                "select * from books where id = :id", params, new PersonMapper()
//        );
//        return null;
//    }
}
