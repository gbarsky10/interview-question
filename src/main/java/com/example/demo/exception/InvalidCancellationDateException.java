package com.example.demo.exception;

public class InvalidCancellationDateException extends RuntimeException {


    private static final long serialVersionUID = 8723652816354702235L;

    public InvalidCancellationDateException(String message) {
        super(message);
    }
}
