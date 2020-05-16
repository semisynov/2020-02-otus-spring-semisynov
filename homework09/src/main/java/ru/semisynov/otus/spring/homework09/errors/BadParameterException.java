package ru.semisynov.otus.spring.homework09.errors;

import ru.semisynov.otus.spring.homework09.errors.enums.ReturnCodeEnum;

public class BadParameterException extends LibraryException {

    public BadParameterException(String message) {
        super(message, ReturnCodeEnum.BAD_PARAMETER);
    }
}