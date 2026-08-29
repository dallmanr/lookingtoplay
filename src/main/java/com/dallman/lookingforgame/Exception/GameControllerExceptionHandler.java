package com.dallman.lookingforgame.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GameControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GameErrorResponse> handleException(GameNotFoundException exc) {
        GameErrorResponse error = new GameErrorResponse();

        error.setHttpStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
