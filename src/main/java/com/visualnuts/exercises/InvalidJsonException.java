package com.visualnuts.exercises;

public class InvalidJsonException extends RuntimeException {

    public InvalidJsonException(Throwable cause) {
        super(cause);
    }

    public InvalidJsonException(String message) {
        super(message);
    }
}
