package ru.semisynov.otus.spring.homework12.errors;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.semisynov.otus.spring.homework12.errors.enums.ReturnCodeEnum;

@Getter
@Setter
@ToString(callSuper = true)
public abstract class LibraryException extends RuntimeException {

    private ReturnCodeEnum returnCodeEnum;

    public LibraryException(String message, ReturnCodeEnum returnCodeEnum) {
        super(message);
        this.returnCodeEnum = returnCodeEnum;
    }
}