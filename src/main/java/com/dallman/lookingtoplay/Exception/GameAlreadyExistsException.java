package com.dallman.lookingtoplay.Exception;

public class GameAlreadyExistsException extends RuntimeException{
    public GameAlreadyExistsException(String message) {
        super(message);
    }

    public GameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
