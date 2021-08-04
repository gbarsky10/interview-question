package com.example.demo.exception;

public class StudentAlreadyEnrolledException extends RuntimeException {

    private static final long serialVersionUID = -317438198250319929L;

    public StudentAlreadyEnrolledException(String message) {
        super(message);
    }
}
