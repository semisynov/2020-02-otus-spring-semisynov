package ru.semisynov.otus.spring.services;

import lombok.AllArgsConstructor;
import ru.semisynov.otus.spring.dao.QuestionDao;
import ru.semisynov.otus.spring.domain.Question;
import ru.semisynov.otus.spring.domain.User;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class QuizImpl implements Quiz {

    private final QuestionDao questionDao;

    private final static String QUIZ_RESULT_TEXT = "Результат для %s %s - всего вопросов %d, правильных ответов %d.";

    public final void startQuiz() {
        List<Question> questions = questionDao.questionList();
        if (questions.size() == 0) {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите фамилию:");
        String lastName = scanner.nextLine();
        System.out.println("Введите имя:");
        String firstName = scanner.nextLine();
        User user = new User(lastName, firstName);

        for (Question question : questions) {
            System.out.println(question.getQuestion());
            String answer = scanner.nextLine();
            if (question.getRightAnswer().toUpperCase().equals(answer.toUpperCase())) {
                user.increaseResult();
            }
        }

        String quizResultText = String.format(QUIZ_RESULT_TEXT, user.getLastName(), user.getFirstName(), questions.size(), user.getResult());
        System.out.println(quizResultText);
    }
}
