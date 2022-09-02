package com.itau.escolaItauSpring.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public String erroGenerico(Exception exception) {
        return exception.getLocalizedMessage();
    }
}
