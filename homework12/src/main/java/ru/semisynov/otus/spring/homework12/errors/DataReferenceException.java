package ru.semisynov.otus.spring.homework12.errors;

import ru.semisynov.otus.spring.homework12.errors.enums.ReturnCodeEnum;

public class DataReferenceException extends LibraryException {

    public DataReferenceException(String message) {
        super(message, ReturnCodeEnum.DATA_REFERENCE);
    }
}