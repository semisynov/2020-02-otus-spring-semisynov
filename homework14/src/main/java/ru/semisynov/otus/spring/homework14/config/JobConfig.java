package ru.semisynov.otus.spring.homework14.config;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
@AllArgsConstructor
@Slf4j
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job importBookJob(Step authorMigrationStep,
                             Step genreMigrationStep,
                             Step bookMigrationStep,
                             Step commentMigrationStep,
                             Step bookGenreMigrationStep,
                             Step bookAuthorMigrationStep) {
        return jobBuilderFactory
                .get("importJob")
                .incrementer(new RunIdIncrementer())
                .start(authorMigrationStep)
                .next(genreMigrationStep)
                .next(bookMigrationStep)
                .next(commentMigrationStep)
                .next(bookGenreMigrationStep)
                .next(bookAuthorMigrationStep)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@Nullable JobExecution jobExecution) {
                        log.info("Начало job" + jobExecution);
                    }

                    @Override
                    public void afterJob(@Nullable JobExecution jobExecution) {
                        log.info("Конец job " + jobExecution);
                    }
                })
                .build();
    }
}
