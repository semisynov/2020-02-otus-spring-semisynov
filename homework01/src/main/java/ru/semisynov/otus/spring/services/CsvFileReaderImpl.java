package ru.semisynov.otus.spring.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;

import java.io.FileReader;
import java.io.IOException;

@AllArgsConstructor
@Log4j2
public class CsvFileReaderImpl implements CsvFileReader {

    private final Resource resource;

    public final FileReader readCsvFile() {
        try {
            return new FileReader(resource.getFile());
        } catch (IOException e) {
            log.error("Error while reading file", e);
        }
        return null;
    }
}
