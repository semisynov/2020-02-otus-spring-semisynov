package ru.semisynov.otus.spring.homework04.services;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.semisynov.otus.spring.homework04.config.ApplicationProperties;
import ru.semisynov.otus.spring.homework04.dao.QuestionDao;
import ru.semisynov.otus.spring.homework04.domain.Question;

import java.util.List;

@Service("quiz")
@AllArgsConstructor
@Slf4j
@ToString
public class QuizImpl implements Quiz {

    private final QuestionDao questionDao;
    private final ApplicationProperties applicationProperties;
    private final PrintService printService;
    private final UserService userService;

    @Override
    public final void startQuiz() {
        int resultMinValue;
        try {
            resultMinValue = Integer.parseInt(applicationProperties.getResult());
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return;
        }

        List<Question> questions = questionDao.questionList();
        if (questions.size() == 0) {
            log.error("Некорректное количиство вопросов");
            return;
        }

        for (Question question : questions) {
            printService.print(question.getQuestion());
            String answer = printService.readLine();
            if (question.getRightAnswer().toUpperCase().equals(answer.toUpperCase())) {
                userService.increaseUserResult();
            }
        }

        int userResult = userService.getUserResult();
        String result;
        if (userResult >= resultMinValue) {
            result = printService.getLineWithLocale("message.result.passed");
        } else {
            result = printService.getLineWithLocale("message.result.failed");
        }
        printService.print(result);

        Object[] argsResultInfo = {userService.getLastName(), userService.getFirstName(), questions.size(), userResult};
        String resultInfo = printService.getLineWithLocale("message.result.info", argsResultInfo);
        printService.print(resultInfo);
    }
}
