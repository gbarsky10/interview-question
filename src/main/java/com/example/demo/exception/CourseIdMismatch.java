package com.example.demo.exception;

public class CourseIdMismatch extends RuntimeException {

    private static final long serialVersionUID = 7746165201462711035L;

    public CourseIdMismatch(String message) {
        super(message);
    }
}
