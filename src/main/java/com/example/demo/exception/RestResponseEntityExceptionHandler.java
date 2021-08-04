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

    /**
     * Handle Course Not Found Exception
     * @param ex
     * @return
     */
    @ExceptionHandler(CourseNotFoundException.class)
    protected ResponseEntity<Object> handleCourseNotFound(CourseNotFoundException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    /**
     * Handle Student Not Found Exception
     * @param ex
     * @return
     */
    @ExceptionHandler(StudentNotFoundException.class)
    protected ResponseEntity<Object> handleStudentNotFound(StudentNotFoundException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    /**
     * Handle Student Already Enrolled Exception
     * @param ex
     * @return
     */
    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    protected ResponseEntity<Object> handleStudentAlreadyEnrolledFound(StudentAlreadyEnrolledException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    /**
     * Handle Course is Full Exception
     * @param ex
     * @return
     */
    @ExceptionHandler(CourseIsFullException.class)
    protected ResponseEntity<Object> handleCourseIsFull(CourseIsFullException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    /**
     * Handle invalid registration data exception
     * @param ex
     * @return
     */
    @ExceptionHandler(InvalidRegistrationDateException.class)
    protected ResponseEntity<Object> handleInvalidRegistrationDate(InvalidRegistrationDateException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    /**
     * Handle invalid cancellation date exception
     * @param ex
     * @return
     */
    @ExceptionHandler(InvalidCancellationDateException.class)
    protected ResponseEntity<Object> handleInvalidCancellationDate(InvalidCancellationDateException ex) {

        ErrorResponse ErrorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return buildResponseEntity(ErrorResponse);
    }

    /**
     * Build Response Entity with Error Response content
     * @param ErrorResponse
     * @return
     */
    private ResponseEntity<Object> buildResponseEntity(ErrorResponse ErrorResponse) {
        return new ResponseEntity<>(ErrorResponse, ErrorResponse.getStatus());
    }

}