package ru.semisynov.otus.spring.homework04.services;

import ru.semisynov.otus.spring.homework04.domain.User;

public interface UserService {

    User registerUser(String defaultLastName, String defaultFirstName);

    void increaseUserResult();

    int getUserResult();

    String getLastName();

    String getFirstName();
}
