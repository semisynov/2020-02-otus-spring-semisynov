package ru.semisynov.otus.spring.services;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.semisynov.otus.spring.dao.QuestionDao;
import ru.semisynov.otus.spring.domain.Question;
import ru.semisynov.otus.spring.domain.User;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Service("quiz")
@ToString
public class QuizImpl implements Quiz {

    private final QuestionDao questionDao;
    private final MessageSource messageSource;
    private final Locale currentLocale;
    private final int resultMinValue;

    public QuizImpl(QuestionDao questionDao,
                    MessageSource messageSource,
                    Locale currentLocale,
                    @Value("${quiz.result.min}") int resultMinValue) {
        this.questionDao = questionDao;
        this.messageSource = messageSource;
        this.currentLocale = currentLocale;
        this.resultMinValue = resultMinValue;
    }

    public final void startQuiz() {
        List<Question> questions = questionDao.questionList();
        if (questions.size() == 0) {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        printLineWithLocale("message.lastName");
        String lastName = scanner.nextLine();
        printLineWithLocale("message.name");
        String firstName = scanner.nextLine();
        User user = new User(lastName, firstName);

        for (Question question : questions) {
            System.out.println(question.getQuestion());
            String answer = scanner.nextLine();
            if (question.getRightAnswer().toUpperCase().equals(answer.toUpperCase())) {
                user.increaseResult();
            }
        }

        int userResult = user.getResult();
        String result;
        if (userResult >= resultMinValue) {
            result = getLineWithLocale("message.result.passed");
        } else {
            result = getLineWithLocale("message.result.failed");
        }
        System.out.println(result);

        Object[] argsResultInfo = {user.getLastName(), user.getFirstName(), questions.size(), userResult};
        String resultInfo = getLineWithLocale("message.result.info", argsResultInfo);
        System.out.println(resultInfo);
    }

    private void printLineWithLocale(String lineParam) {
        System.out.println(getLineWithLocale(lineParam));
    }

    private String getLineWithLocale(String lineParam) {
        return messageSource.getMessage(lineParam, null, currentLocale);
    }

    private String getLineWithLocale(String lineParam, Object[] args) {
        return messageSource.getMessage(lineParam, args, currentLocale);
    }
}
