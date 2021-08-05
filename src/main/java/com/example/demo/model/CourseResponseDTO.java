package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated
@Getter @Setter
public class CourseResponseDTO {

    private Long id;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private int capacity;

    private int remaining;

    private Set<StudentDTO> participants;
}
