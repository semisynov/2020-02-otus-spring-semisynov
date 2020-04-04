package ru.semisynov.otus.spring.homework04.services;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.semisynov.otus.spring.homework04.errors.BadParameterException;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

@Component("csvFileReader")
@Slf4j
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
            if (url == null) {
                throw new BadParameterException("File not found");
            }
            List<String> allLines = Files.readAllLines(Paths.get(url.getFile()));
            if (allLines.size() == 0) {
                throw new BadParameterException("Question file is empty");
            }
            return allLines;
        } catch (IOException e) {
            throw new BadParameterException("Error while reading file");
        }
    }
}
