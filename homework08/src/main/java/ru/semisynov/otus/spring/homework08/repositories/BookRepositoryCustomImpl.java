package ru.semisynov.otus.spring.homework08.repositories;

import lombok.RequiredArgsConstructor;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Comment;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteBook(Book book) {
        mongoTemplate.remove(book);

        Query queryComment = new Query();
        queryComment.addCriteria(Criteria.where("bookId").is(book.getId()));
        mongoTemplate.remove(queryComment, Comment.class);
    }
}
