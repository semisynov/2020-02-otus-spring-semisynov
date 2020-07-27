package ru.semisynov.otus.spring.homework14.config.steps.item.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.lang.Nullable;

@Slf4j
public class CustomChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(@Nullable ChunkContext chunkContext) {
        log.info("Начало пачки");
    }

    @Override
    public void afterChunk(@Nullable ChunkContext chunkContext) {
        log.info("Конец пачки");
    }

    @Override
    public void afterChunkError(@Nullable ChunkContext chunkContext) {
        log.info("Ошибка пачки");
    }
}
