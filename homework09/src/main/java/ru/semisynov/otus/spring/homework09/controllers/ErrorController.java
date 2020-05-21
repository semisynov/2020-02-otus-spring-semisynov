package ru.semisynov.otus.spring.homework09.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.semisynov.otus.spring.homework09.errors.BadParameterException;
import ru.semisynov.otus.spring.homework09.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework09.errors.ItemNotFoundException;

@Slf4j
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(DataReferenceException.class)
    public ModelAndView handleDataReferenceException(DataReferenceException e) {
        ModelAndView modelAndView = new ModelAndView("error/reference-exception");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ModelAndView handleItemNotFoundException(ItemNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error/not-found-exception");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(BadParameterException.class)
    public ModelAndView handleBadParameterException(BadParameterException e) {
        ModelAndView modelAndView = new ModelAndView("error/bad-parameter-exception");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}