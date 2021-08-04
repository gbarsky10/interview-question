package com.example.demo.exception;

import com.example.demo.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    protected ResponseEntity<Object> handleCourseNotFound(CourseNotFoundException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    protected ResponseEntity<Object> handleStudentNotFound(StudentNotFoundException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }


    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    protected ResponseEntity<Object> handleStudentAlreadyEnrolledFound(StudentAlreadyEnrolledException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    @ExceptionHandler(CourseIsFullException.class)
    protected ResponseEntity<Object> handleCourseIsFull(CourseIsFullException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    @ExceptionHandler(InvalidRegistrationDateException.class)
    protected ResponseEntity<Object> handleInvalidRegistrationDate(InvalidRegistrationDateException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    @ExceptionHandler(InvalidCancellationDateException.class)
    protected ResponseEntity<Object> handleInvalidCancellationDate(InvalidCancellationDateException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse ErrorResponse) {
        return new ResponseEntity<>(ErrorResponse, ErrorResponse.getStatus());
    }

}