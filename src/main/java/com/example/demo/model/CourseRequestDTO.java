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
public class CourseRequestDTO {

     private String title;

     private LocalDate startDate;

     private LocalDate endDate;

     private int capacity;

}
