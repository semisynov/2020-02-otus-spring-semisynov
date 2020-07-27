package ru.semisynov.otus.spring.homework14.config.steps.item.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.lang.Nullable;

import java.util.List;

@Slf4j
public class CustomItemWriteListener<A> implements ItemWriteListener<A> {

    @Override
    public void beforeWrite(@Nullable List<? extends A> list) {
        log.info("Начало записи");
    }

    @Override
    public void afterWrite(@Nullable List<? extends A> list) {
        log.info("Конец записи");
    }

    @Override
    public void onWriteError(@Nullable Exception e, @Nullable List<? extends A> list) {
        log.info("Ошибка записи");
    }
}
