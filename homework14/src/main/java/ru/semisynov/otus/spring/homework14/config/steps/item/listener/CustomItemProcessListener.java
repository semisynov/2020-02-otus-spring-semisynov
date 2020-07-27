package ru.semisynov.otus.spring.homework14.config.steps.item.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.lang.Nullable;

@Slf4j
public class CustomItemProcessListener<A, S> implements ItemProcessListener<A, S> {

    @Override
    public void beforeProcess(@Nullable A a) {
        log.info("Начало обработки");
    }

    @Override
    public void afterProcess(@Nullable A a, @Nullable S s) {
        log.info("Конец обработки");
    }

    @Override
    public void onProcessError(@Nullable A a, @Nullable Exception e) {
        log.info("Ошбка обработки");
    }
}
