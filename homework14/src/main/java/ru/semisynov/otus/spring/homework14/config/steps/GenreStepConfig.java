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
import ru.semisynov.otus.spring.homework14.model.Genre;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@AllArgsConstructor
public class GenreStepConfig {

    public static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final DataSource dataSource;

    @Bean
    public Step genreMigrationStep(MongoItemReader<Genre> genreReader, ItemWriter<Genre> genreWriter) {
        return stepBuilderFactory.get("genreMigrationStep")
                .allowStartIfComplete(true)
                .<Genre, Genre>chunk(CHUNK_SIZE)
                .reader(genreReader)
                .writer(genreWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Начало чтения");
                    }

                    public void afterRead(@Nullable Genre o) {
                        log.info("Конец чтения");
                    }

                    public void onReadError(@Nullable Exception e) {
                        log.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<Genre>() {
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
                .listener(new ItemProcessListener<Genre, Genre>() {
                    public void beforeProcess(@Nullable Genre o) {
                        log.info("Начало обработки");
                    }

                    public void afterProcess(@Nullable Genre o, @Nullable Genre o2) {
                        log.info("Конец обработки");
                    }

                    public void onProcessError(@Nullable Genre o, @Nullable Exception e) {
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
    public MongoItemReader<Genre> genreReader() {
        return new MongoItemReaderBuilder<Genre>()
                .targetType(Genre.class)
                .name("genreReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .sorts(Map.of("title", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemWriter<Genre> genreWriter() {
        return new JdbcBatchItemWriterBuilder<Genre>()
                .sql("INSERT INTO genres (genre_id, title) VALUES (:id, :title)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .build();
    }
}
