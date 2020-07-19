package ru.semisynov.otus.spring.homework13.errors.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReturnCodeEnum {
    BAD_PARAMETER("bad_parameter"),
    ITEM_NOT_FOUND("item_not_found"),
    DATA_REFERENCE("data_reference");

    @Getter
    private final String name;
}