package ru.semisynov.otus.spring.homework11.errors;

import ru.semisynov.otus.spring.homework11.errors.enums.ReturnCodeEnum;

public class ItemNotFoundException extends LibraryException {

    public ItemNotFoundException(String message) {
        super(message, ReturnCodeEnum.ITEM_NOT_FOUND);
    }
}