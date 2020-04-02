package ru.semisynov.otus.spring.homework04.dao;

import ru.semisynov.otus.spring.homework04.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> questionList();
}
