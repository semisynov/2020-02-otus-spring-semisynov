package ru.semisynov.otus.spring.homework11.errors;

import ru.semisynov.otus.spring.homework11.errors.enums.ReturnCodeEnum;

public class DataReferenceException extends LibraryException {

    public DataReferenceException(String message) {
        super(message, ReturnCodeEnum.DATA_REFERENCE);
    }
}