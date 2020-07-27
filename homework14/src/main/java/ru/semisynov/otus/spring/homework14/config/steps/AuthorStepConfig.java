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
import ru.semisynov.otus.spring.homework14.model.Author;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Configuration
@AllArgsConstructor
public class AuthorStepConfig {

    public static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final DataSource dataSource;

    @Bean
    public Step authorMigrationStep(MongoItemReader<Author> authorReader, ItemWriter<Author> authorWriter) {
        return stepBuilderFactory.get("authorMigrationStep")
                .allowStartIfComplete(true)
                .<Author, Author>chunk(CHUNK_SIZE)
                .reader(authorReader)
                .writer(authorWriter)
                .listener(new CustomItemReadListener<>())
                .listener(new CustomItemWriteListener<>())
                .listener(new CustomItemProcessListener<>())
                .listener(new CustomChunkListener())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public MongoItemReader<Author> authorReader() {
        return new MongoItemReaderBuilder<Author>()
                .targetType(Author.class)
                .name("authorReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .sorts(Map.of("_id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemWriter<Author> authorWriter() {
        return new JdbcBatchItemWriterBuilder<Author>()
                .sql("INSERT INTO authors (author_id, name) VALUES (:id, :name)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .build();
    }
}
