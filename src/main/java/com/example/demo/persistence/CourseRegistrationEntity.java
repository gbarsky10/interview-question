package com.example.demo.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name="course_registration")
public class CourseRegistrationEntity implements Serializable {

    private static final long serialVersionUID = 6138004981734343571L;

    @EmbeddedId
    CourseRegistrationKey id;

    @ManyToOne
    //@MapsId("studentId")
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    StudentEntity student;

    @ManyToOne
    //@MapsId("courseId")
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    CourseEntity course;

    private LocalDate registrationDate;;

    public CourseRegistrationEntity(CourseEntity course, StudentEntity student, LocalDate registrationDate) {
        this.id = new CourseRegistrationKey(student.getId(), course.getId());
        this.student = student;
        this.course = course;
        this.registrationDate = registrationDate;
    }
}