package ru.semisynov.otus.spring.homework13.errors;

import ru.semisynov.otus.spring.homework13.errors.enums.ReturnCodeEnum;

public class ItemNotFoundException extends LibraryException {

    public ItemNotFoundException(String message) {
        super(message, ReturnCodeEnum.ITEM_NOT_FOUND);
    }
}