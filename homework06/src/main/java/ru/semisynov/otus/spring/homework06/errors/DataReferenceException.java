package ru.semisynov.otus.spring.homework06.errors;

import ru.semisynov.otus.spring.homework06.errors.enums.ReturnCodeEnum;

public class DataReferenceException extends LibraryException {

    public DataReferenceException(String message) {
        super(message, ReturnCodeEnum.DATA_REFERENCE);
    }
}