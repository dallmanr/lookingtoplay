package com.dallman.lookingtoplay.exception;

public class GameAlreadyExistsException extends RuntimeException{
    public GameAlreadyExistsException(String message) {
        super(message);
    }
}
