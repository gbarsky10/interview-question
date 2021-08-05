package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
@Getter @Setter
public class StudentDTO {

    private LocalDate registrationDate;

    private String name;

}
