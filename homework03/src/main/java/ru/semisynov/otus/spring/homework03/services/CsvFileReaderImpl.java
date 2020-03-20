package ru.semisynov.otus.spring.homework03.services;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

@Component("csvFileReader")
@Log4j2
@ToString
public class CsvFileReaderImpl implements CsvFileReader {

    private final Resource resource;
    private final String fileName;

    public CsvFileReaderImpl(MessageSource messageSource, Locale currentLocale) {
        this.fileName = messageSource.getMessage("csv.file", null, currentLocale);
        this.resource = new ClassPathResource(fileName);
    }

    @Override
    public final FileReader readCsvFile() {
        try {
            return new FileReader(resource.getFile());
        } catch (IOException e) {
            log.error("Error while reading file", e);
        }
        return null;
    }
}
