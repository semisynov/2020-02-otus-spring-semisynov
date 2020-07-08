package ru.semisynov.otus.spring.homework12.services;

import ru.semisynov.otus.spring.homework12.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUserById(long id);

    User saveUser(User user);

    void deleteUserById(long id);
}