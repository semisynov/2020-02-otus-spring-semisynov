package ru.semisynov.otus.spring.homework14.config.steps;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.Nullable;
import ru.semisynov.otus.spring.homework14.model.Book;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@AllArgsConstructor
public class BookStepConfig {

    public static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final DataSource dataSource;

    @Bean
    public Step bookMigrationStep(MongoItemReader<Book> bookReader, ItemWriter<Book> bookWriter) {
        return stepBuilderFactory.get("bookMigrationStep")
                .allowStartIfComplete(true)
                .<Book, Book>chunk(CHUNK_SIZE)
                .reader(bookReader)
                .writer(bookWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Начало чтения");
                    }

                    public void afterRead(@Nullable Book o) {
                        log.info("Конец чтения");
                    }

                    public void onReadError(@Nullable Exception e) {
                        log.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<Book>() {
                    public void beforeWrite(@Nullable List list) {
                        log.info("Начало записи");
                    }

                    public void afterWrite(@Nullable List list) {
                        log.info("Конец записи");
                    }

                    public void onWriteError(@Nullable Exception e, @Nullable List list) {
                        log.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<Book, Book>() {
                    public void beforeProcess(@Nullable Book o) {
                        log.info("Начало обработки");
                    }

                    public void afterProcess(@Nullable Book o, @Nullable Book o2) {
                        log.info("Конец обработки");
                    }

                    public void onProcessError(@Nullable Book o, @Nullable Exception e) {
                        log.info("Ошбка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@Nullable ChunkContext chunkContext) {
                        log.info("Начало пачки");
                    }

                    public void afterChunk(@Nullable ChunkContext chunkContext) {
                        log.info("Конец пачки");
                    }

                    public void afterChunkError(@Nullable ChunkContext chunkContext) {
                        log.info("Ошибка пачки");
                    }
                })
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public MongoItemReader<Book> bookReader() {
        return new MongoItemReaderBuilder<Book>()
                .targetType(Book.class)
                .name("bookReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .sorts(Map.of("title", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemWriter<Book> bookWriter() {
        return new JdbcBatchItemWriterBuilder<Book>()
                .sql("INSERT INTO books (book_id, title) VALUES (:id, :title)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .build();
    }
}
