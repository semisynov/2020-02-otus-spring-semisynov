package ru.semisynov.otus.spring.homework08.errors;

import ru.semisynov.otus.spring.homework08.errors.enums.ReturnCodeEnum;

public class BadParameterException extends LibraryException {

    public BadParameterException(String message) {
        super(message, ReturnCodeEnum.BAD_PARAMETER);
    }
}