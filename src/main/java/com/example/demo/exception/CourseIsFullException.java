package com.example.demo.exception;

public class CourseIsFullException extends RuntimeException {

    private static final long serialVersionUID = -7758218049512945707L;

    public CourseIsFullException(String message) {
        super(message);
    }
}

