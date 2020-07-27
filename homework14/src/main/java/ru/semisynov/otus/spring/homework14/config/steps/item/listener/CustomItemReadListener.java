package ru.semisynov.otus.spring.homework14.config.steps.item.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.lang.Nullable;

@Slf4j
public class CustomItemReadListener<A> implements ItemReadListener<A> {

    @Override
    public void beforeRead() {
        log.info("Начало чтения");
    }

    @Override
    public void afterRead(@Nullable Object o) {
        log.info("Конец чтения");
    }

    @Override
    public void onReadError(@Nullable Exception e) {
        log.info("Ошибка чтения");
    }
}
