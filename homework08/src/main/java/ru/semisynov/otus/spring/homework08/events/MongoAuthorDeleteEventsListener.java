package ru.semisynov.otus.spring.homework08.events;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import ru.semisynov.otus.spring.homework08.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework08.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework08.model.Author;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework08.repositories.BookRepository;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoAuthorDeleteEventsListener extends AbstractMongoEventListener<Author> {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private static final String TEXT_NOT_FOUND = "Author not found";

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        Document source = event.getSource();
        String id = source.get("_id").toString();
        Author author = authorRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        List<Book> books = bookRepository.findByAuthors(author);

        if (books.size() != 0) {
            throw new DataReferenceException(String.format("Unable to delete the author %s there are links in the database", id));
        }
    }
}
