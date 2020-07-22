package ru.semisynov.otus.spring.homework13.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserRole {
    ROLE_USER("Пользователь"),
    ROLE_ADMIN("Администратор"),
    ROLE_CHILD("Ребёнок");

    @Getter
    private final String value;
}
