package ru.semisynov.otus.spring.homework04.services;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Scanner;

@Service

public class PrintServiceImpl implements PrintService {

    private final MessageSource messageSource;
    private final Locale currentLocale;
    private final Scanner scanner;

    public PrintServiceImpl(MessageSource messageSource, Locale currentLocale) {
        this.messageSource = messageSource;
        this.currentLocale = currentLocale;
        this.scanner = new Scanner(System.in);
    }

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

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
