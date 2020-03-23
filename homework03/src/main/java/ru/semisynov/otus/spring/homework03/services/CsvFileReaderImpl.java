package ru.semisynov.otus.spring.homework03.services;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

@Component("csvFileReader")
@Log4j2
@ToString
public class CsvFileReaderImpl implements CsvFileReader {

    private final String fileName;

    public CsvFileReaderImpl(MessageSource messageSource, Locale currentLocale) {
        this.fileName = messageSource.getMessage("csv.file", null, currentLocale);
    }

    @Override
    public final List<String> readAllLines() {
        try {
            URL url = ClassLoader.getSystemResource(fileName);
            return Files.readAllLines(Paths.get(url.getFile()));
        } catch (IOException e) {
            log.error("Error while reading file", e);
            return null;
        }
    }
}
