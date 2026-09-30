package com.dallman.lookingtoplay.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
* Controller Advice because its for managing our GameController which returns thymeleaf views and is NOT a RestController.
* We can add errors to our model and then check against the errors in tests instead of checking http statuses which would
* usually be set when errors occur on REST APIs.
* */

@ControllerAdvice
public class GameControllerExceptionHandler {

    @ExceptionHandler
    public String handleExceptionGameNotFoundException(GameNotFoundException exc, Model model) {
        model.addAttribute("error", exc.getMessage());

        GameErrorResponse error = new GameErrorResponse();

        error.setHttpStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return "error";
    }

    @ExceptionHandler
    public String handleGameAlreadyExistsException(GameAlreadyExistsException exc, Model model) {
        model.addAttribute("error", exc.getMessage());

        return "/games/addnewgame";
    }
}
