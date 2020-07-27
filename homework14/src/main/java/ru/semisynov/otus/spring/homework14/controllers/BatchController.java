package ru.semisynov.otus.spring.homework14.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent("batchController")
@RequiredArgsConstructor
public class BatchController {
    private final Job importBookJob;
    private final JobLauncher jobLauncher;

    @ShellMethod(value = "Start migration", key = {"s", "start"})
    public void startMigrationJob() throws Exception {
        jobLauncher.run(importBookJob, new JobParameters());
    }
}