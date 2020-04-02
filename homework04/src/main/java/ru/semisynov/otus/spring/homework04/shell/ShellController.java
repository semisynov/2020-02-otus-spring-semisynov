package ru.semisynov.otus.spring.homework04.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.semisynov.otus.spring.homework04.domain.User;
import ru.semisynov.otus.spring.homework04.services.PrintService;
import ru.semisynov.otus.spring.homework04.services.Quiz;
import ru.semisynov.otus.spring.homework04.services.UserService;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final PrintService printService;
    private final UserService userService;
    private final Quiz quiz;
    private User user;

    @ShellMethod(value = "Register user command", key = {"l", "login"})
    public String register() {
        this.user = userService.registerUser();
        Object[] argsResultInfo = {this.user.getLastName(), this.user.getFirstName()};
        String loginInfo = printService.getLineWithLocale("message.shell.hello", argsResultInfo);
        return loginInfo;
    }

    @ShellMethod(value = "Start quiz command", key = {"s", "start"})
    @ShellMethodAvailability(value = "isUserRegistered")
    public String startQuiz() {
        quiz.startQuiz();
        return printService.getLineWithLocale("message.shell.end");
    }

    private Availability isUserRegistered() {
        if (this.user == null) {
            return Availability.unavailable(printService.getLineWithLocale("message.shell.login"));
        } else {
            return Availability.available();
        }
    }
}
