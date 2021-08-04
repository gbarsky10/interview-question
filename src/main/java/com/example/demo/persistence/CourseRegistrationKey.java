package com.example.demo.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CourseRegistrationKey implements Serializable {

    private static final long serialVersionUID = 4943435188934022359L;

    @Column(name = "student_id")
    Long studentId;

    @Column(name = "course_id")
    Long courseId;

}
