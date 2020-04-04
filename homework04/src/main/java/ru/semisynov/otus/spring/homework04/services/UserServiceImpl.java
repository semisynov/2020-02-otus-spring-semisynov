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
    public User registerUser(String defaultLastName, String defaultFirstName) {
        printService.printLineWithLocale("message.lastName");
        String lastName = printService.readLine();
        if (lastName.isEmpty()) {
            lastName = defaultLastName;
        }
        this.user.setLastName(lastName);

        printService.printLineWithLocale("message.firstName");
        String firstName = printService.readLine();
        if (firstName.isEmpty()) {
            firstName = defaultFirstName;
        }
        this.user.setFirstName(firstName);
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
