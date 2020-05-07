package ru.semisynov.otus.spring.homework08.events;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import ru.semisynov.otus.spring.homework08.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework08.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Genre;
import ru.semisynov.otus.spring.homework08.repositories.BookRepository;
import ru.semisynov.otus.spring.homework08.repositories.GenreRepository;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoGenreDeleteEventsListener extends AbstractMongoEventListener<Genre> {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private static final String TEXT_NOT_FOUND = "Genre not found";

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);
        Document source = event.getSource();
        String id = source.get("_id").toString();
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        List<Book> books = bookRepository.findByGenres(genre);

        if (books.size() != 0) {
            throw new DataReferenceException(String.format("Unable to delete the genre %s there are links in the database", id));
        }
    }
}
