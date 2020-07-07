package ru.semisynov.otus.spring.homework11.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.semisynov.otus.spring.homework11.model.Book;
import ru.semisynov.otus.spring.homework11.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findAllByBookId(String bookId);

    Mono<Void> deleteAllByBook(Book book);
}