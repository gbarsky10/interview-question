package com.example.demo.exception;

public class CourseNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -479675961293170489L;

    public CourseNotFoundException(String message) {
        super(message);
    }
}


