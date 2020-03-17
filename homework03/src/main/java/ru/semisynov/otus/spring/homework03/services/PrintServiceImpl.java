package ru.semisynov.otus.spring.homework03.services;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class PrintServiceImpl implements PrintService {

    private final MessageSource messageSource;
    private final Locale currentLocale;

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void printLineWithLocale(String lineParam) {
        print(getLineWithLocale(lineParam));
    }

    @Override
    public String getLineWithLocale(String lineParam) {
        return messageSource.getMessage(lineParam, null, currentLocale);
    }

    @Override
    public String getLineWithLocale(String lineParam, Object[] args) {
        return messageSource.getMessage(lineParam, args, currentLocale);
    }
}
