package ru.semisynov.otus.spring.homework14.config.steps;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.semisynov.otus.spring.homework14.config.steps.item.listener.CustomChunkListener;
import ru.semisynov.otus.spring.homework14.config.steps.item.listener.CustomItemProcessListener;
import ru.semisynov.otus.spring.homework14.config.steps.item.listener.CustomItemReadListener;
import ru.semisynov.otus.spring.homework14.config.steps.item.listener.CustomItemWriteListener;
import ru.semisynov.otus.spring.homework14.config.steps.item.writer.BookAuthorItemWriter;
import ru.semisynov.otus.spring.homework14.model.Book;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Configuration
@AllArgsConstructor
public class BookAuthorStepConfig {

    public static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final DataSource dataSource;

    @Bean
    public Step bookAuthorMigrationStep(MongoItemReader<Book> bookAuthorReader, ItemWriter<Book> bookAuthorWriter) {
        return stepBuilderFactory.get("bookAuthorMigrationStep")
                .allowStartIfComplete(true)
                .<Book, Book>chunk(CHUNK_SIZE)
                .reader(bookAuthorReader)
                .writer(bookAuthorWriter)
                .listener(new CustomItemReadListener<>())
                .listener(new CustomItemWriteListener<>())
                .listener(new CustomItemProcessListener<>())
                .listener(new CustomChunkListener())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public MongoItemReader<Book> bookAuthorReader() {
        return new MongoItemReaderBuilder<Book>()
                .targetType(Book.class)
                .name("bookAuthorReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .sorts(Map.of("title", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemWriter<Book> bookAuthorWriter() {
        return new BookAuthorItemWriter(dataSource);
    }
}
