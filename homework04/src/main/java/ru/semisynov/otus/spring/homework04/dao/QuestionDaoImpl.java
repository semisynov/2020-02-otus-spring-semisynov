package ru.semisynov.otus.spring.homework04.dao;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Repository;
import ru.semisynov.otus.spring.homework04.domain.Question;
import ru.semisynov.otus.spring.homework04.services.CsvFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository("questionDao")
@AllArgsConstructor
@Slf4j
@ToString
public class QuestionDaoImpl implements QuestionDao {

    private final CsvFileReader csvFileReader;

    public final List<Question> questionList() {
        List<Question> questions = new ArrayList<>();
        try {
            Iterable<String> allQuestionLines = csvFileReader.readAllLines();
            for (String questionLine : allQuestionLines) {
                CSVParser parser = CSVParser.parse(questionLine, CSVFormat.DEFAULT);
                parser.getRecords().forEach(r -> questions.add(new Question(r.get(0), r.get(1))));
            }
        } catch (IllegalArgumentException | IOException e) {
            log.error("Error wile parsing questions", e);
        }
        return questions;
    }
}
