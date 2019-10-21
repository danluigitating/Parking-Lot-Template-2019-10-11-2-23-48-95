package com.thoughtworks.parking_lot.handler;

import com.thoughtworks.parking_lot.error.CustomError;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomError handleNotFoundException(NotFoundException e){
        CustomError customError = new CustomError();
        customError.setErrorCode(404);
        customError.setErrorMessage(e.getMessage());

        return customError;
    }

}
