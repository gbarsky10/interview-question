package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;


@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
@Data
public class StudentEnrollmentRequestDTO {

    private Long courseId;

    private LocalDate registrationDate;

    private String name;

}
