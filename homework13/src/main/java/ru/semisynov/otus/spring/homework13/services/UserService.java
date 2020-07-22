package ru.semisynov.otus.spring.homework13.services;

import ru.semisynov.otus.spring.homework13.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUserById(long id);

    User saveUser(User user);

    void deleteUserById(long id);
}