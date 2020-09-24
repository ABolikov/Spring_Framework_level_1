package ru.bolikov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.bolikov.exception.NotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundExceptionHandler(NotFoundException notFoundException) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("entity", notFoundException.getNameEntity() + " " + HttpStatus.NOT_FOUND.getReasonPhrase());
        modelAndView.addObject("httpStatus", HttpStatus.NOT_FOUND.value());
        return modelAndView;
    }
}
