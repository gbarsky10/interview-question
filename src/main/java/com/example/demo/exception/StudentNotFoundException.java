package com.example.demo.exception;

public class StudentNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 261566390026480714L;

    public StudentNotFoundException(String message) {
        super(message);
    }
}


