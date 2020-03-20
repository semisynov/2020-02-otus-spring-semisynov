package ru.semisynov.otus.spring.dao;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.domain.Question;
import ru.semisynov.otus.spring.services.CsvFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository("questionDao")
@AllArgsConstructor
@Log4j2
@ToString
public class QuestionDaoImpl implements QuestionDao {

    private final CsvFileReader csvFileReader;

    public final List<Question> questionList() {
        List<Question> questions = new ArrayList<>();
        try {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(csvFileReader.readCsvFile());
            for (CSVRecord record : records) {
                String columnOne = record.get(0);
                String columnTwo = record.get(1);
                questions.add(new Question(columnOne, columnTwo));
            }
        } catch (IllegalArgumentException | IOException e) {
            log.error("Error wile parsing questions", e);
        }
        return questions;
    }
}
