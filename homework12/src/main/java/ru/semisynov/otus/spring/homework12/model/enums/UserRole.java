package ru.semisynov.otus.spring.homework12.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserRole {
    ROLE_USER("Пользователь"),
    ROLE_ADMIN("Администратор");

    @Getter
    private final String value;
}
