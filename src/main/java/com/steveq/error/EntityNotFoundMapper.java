package com.steveq.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Adam on 2017-06-05.
 */
@ControllerAdvice
public class EntityNotFoundMapper {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Zasób nie isnieje")
    public void handleNotFoune(){}
}
