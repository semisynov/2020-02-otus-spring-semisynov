package ru.semisynov.otus.spring.homework11.errors;

import ru.semisynov.otus.spring.homework11.errors.enums.ReturnCodeEnum;

public class BadParameterException extends LibraryException {

    public BadParameterException(String message) {
        super(message, ReturnCodeEnum.BAD_PARAMETER);
    }
}