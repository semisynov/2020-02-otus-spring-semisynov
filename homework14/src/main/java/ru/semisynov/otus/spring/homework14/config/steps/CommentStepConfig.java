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
import ru.semisynov.otus.spring.homework14.model.Comment;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Configuration
@AllArgsConstructor
public class CommentStepConfig {

    public static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final DataSource dataSource;

    @Bean
    public Step commentMigrationStep(MongoItemReader<Comment> commentReader, ItemWriter<Comment> commentWriter) {
        return stepBuilderFactory.get("commentMigrationStep")
                .allowStartIfComplete(true)
                .<Comment, Comment>chunk(CHUNK_SIZE)
                .reader(commentReader)
                .writer(commentWriter)
                .listener(new CustomItemReadListener<>())
                .listener(new CustomItemWriteListener<>())
                .listener(new CustomItemProcessListener<>())
                .listener(new CustomChunkListener())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public MongoItemReader<Comment> commentReader() {
        return new MongoItemReaderBuilder<Comment>()
                .targetType(Comment.class)
                .name("commentReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .sorts(Map.of("dateTime", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemWriter<Comment> commentWriter() {
        return new JdbcBatchItemWriterBuilder<Comment>()
                .sql("INSERT INTO comments (comment_id, book_id, date_time, text) VALUES (:id, :book.id, :dateTime, :text)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .build();
    }
}
