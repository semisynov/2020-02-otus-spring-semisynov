package ru.semisynov.otus.spring.homework03.services;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.semisynov.otus.spring.homework03.config.ApplicationProperties;
import ru.semisynov.otus.spring.homework03.dao.QuestionDao;
import ru.semisynov.otus.spring.homework03.domain.Question;
import ru.semisynov.otus.spring.homework03.domain.User;

import java.util.List;
import java.util.Scanner;

@Service("quiz")
@AllArgsConstructor
@Log4j2
@ToString
public class QuizImpl implements Quiz {

    private final QuestionDao questionDao;
    private final ApplicationProperties applicationProperties;
    private final PrintService printService;

    @Override
    public final void startQuiz() {
        int resultMinValue;
        try {
            resultMinValue = Integer.parseInt(applicationProperties.getResult());
        } catch (NumberFormatException e) {
            log.error(e);
            return;
        }

        List<Question> questions = questionDao.questionList();
        if (questions.size() == 0) {
            log.error("Некорректное количиство вопросов");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        printService.printLineWithLocale("message.lastName");
        String lastName = scanner.nextLine();
        printService.printLineWithLocale("message.name");
        String firstName = scanner.nextLine();
        User user = new User(lastName, firstName);

        for (Question question : questions) {
            printService.print(question.getQuestion());
            String answer = scanner.nextLine();
            if (question.getRightAnswer().toUpperCase().equals(answer.toUpperCase())) {
                user.increaseResult();
            }
        }

        int userResult = user.getResult();
        String result;
        if (userResult >= resultMinValue) {
            result = printService.getLineWithLocale("message.result.passed");
        } else {
            result = printService.getLineWithLocale("message.result.failed");
        }
        printService.print(result);

        Object[] argsResultInfo = {user.getLastName(), user.getFirstName(), questions.size(), userResult};
        String resultInfo = printService.getLineWithLocale("message.result.info", argsResultInfo);
        printService.print(resultInfo);
    }
}
