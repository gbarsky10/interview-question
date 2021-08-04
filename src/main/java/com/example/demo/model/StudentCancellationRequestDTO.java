package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Generated;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
@Data
public class StudentCancellationRequestDTO {

    private Long courseId;

    private LocalDate cancelDate;

    private String name;

}
