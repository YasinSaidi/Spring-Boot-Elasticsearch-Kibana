package com.course.practicaljava.rest.domain;

public class ErrorResponse {

    //It contains error message
    private String message;

    //It contains timestamp when error happens
    private long timestamp;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
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