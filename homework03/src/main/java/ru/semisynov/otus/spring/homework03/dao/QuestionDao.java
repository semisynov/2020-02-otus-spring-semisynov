package ru.semisynov.otus.spring.homework03.dao;

import ru.semisynov.otus.spring.homework03.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> questionList();
}
