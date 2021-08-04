package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
@Data
public class StudentDTO {

    private LocalDate registrationDate;

    private String name;

}
