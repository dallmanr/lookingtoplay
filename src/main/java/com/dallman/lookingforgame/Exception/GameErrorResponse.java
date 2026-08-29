package com.dallman.lookingforgame.Exception;


public class GameErrorResponse {

    private int httpStatus;
    private String message;
    private long timestamp;

    public GameErrorResponse() {
    }

    public GameErrorResponse(int httpStatus, String message, long timestamp) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
