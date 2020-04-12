package ru.semisynov.otus.spring.homework05.services;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {


    private final Scanner scanner;

    public IOServiceImpl(MessageSource messageSource) {

        this.scanner = new Scanner(System.in);
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
