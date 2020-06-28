package ru.semisynov.otus.spring.homework11.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.semisynov.otus.spring.homework11.model.Author;
import ru.semisynov.otus.spring.homework11.model.Book;
import ru.semisynov.otus.spring.homework11.model.Genre;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findByAuthors(Author author);

    Flux<Book> findByGenres(Genre genre);
}