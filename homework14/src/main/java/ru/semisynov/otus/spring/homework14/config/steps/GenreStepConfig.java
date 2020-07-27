package ru.semisynov.otus.spring.homework14.config.steps;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
import ru.semisynov.otus.spring.homework14.config.steps.item.listener.CustomChunkListener;
import ru.semisynov.otus.spring.homework14.config.steps.item.listener.CustomItemProcessListener;
import ru.semisynov.otus.spring.homework14.config.steps.item.listener.CustomItemReadListener;
import ru.semisynov.otus.spring.homework14.config.steps.item.listener.CustomItemWriteListener;
import ru.semisynov.otus.spring.homework14.model.Genre;

import javax.sql.DataSource;
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
                .listener(new CustomItemReadListener<>())
                .listener(new CustomItemWriteListener<>())
                .listener(new CustomItemProcessListener<>())
                .listener(new CustomChunkListener())
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
