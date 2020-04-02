package ru.semisynov.otus.spring.homework04.services;

import org.springframework.stereotype.Service;
import ru.semisynov.otus.spring.homework04.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final PrintService printService;
    private final User user;

    public UserServiceImpl(PrintService printService) {
        this.printService = printService;
        this.user = new User();
    }

    @Override
    public User registerUser() {
        printService.printLineWithLocale("message.lastName");
        this.user.setLastName(printService.readLine());
        printService.printLineWithLocale("message.firstName");
        this.user.setFirstName(printService.readLine());
        return this.user;
    }

    @Override
    public String getLastName() {
        return this.user.getLastName();
    }

    @Override
    public String getFirstName() {
        return this.user.getFirstName();
    }

    @Override
    public void increaseUserResult() {
        this.user.increaseResult();
    }

    @Override
    public int getUserResult() {
        return this.user.getResult();
    }
}
