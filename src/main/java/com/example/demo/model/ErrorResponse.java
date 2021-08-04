package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Generated
public class ErrorResponse {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> subErrors;

    public ErrorResponse(LocalDateTime timestamp, HttpStatus status,String message, List<String> subErrors){
        this.status = status;
        this.message = message;
        this.subErrors = subErrors;
        this.timestamp = timestamp;
    }
}