package ru.semisynov.otus.spring.dao;

import ru.semisynov.otus.spring.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> questionList();
}
