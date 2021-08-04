package com.example.demo.exception;

public class InvalidRegistrationDateException extends RuntimeException {

    private static final long serialVersionUID = -6204127175211137028L;

    public InvalidRegistrationDateException(String message) {
        super(message);
    }
}
