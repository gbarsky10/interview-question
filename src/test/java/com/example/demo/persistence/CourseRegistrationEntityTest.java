package com.example.demo.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.example.demo.TestHelper.buildCourse;
import static com.example.demo.TestHelper.buildStudent;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseRegistrationEntityTest {

    public static final String EXPECTED_STUDENT_NAME = "Matt";
    public static final String EXPECTED_COURSE_TITLE = "Math";
    public static final LocalDate EXPECTED_REGISTRATION_DATE = LocalDate.now();
    private StudentEntity student;
    private CourseEntity course;
    private CourseRegistrationEntity cre;

    @BeforeEach
    public void setUp() throws Exception {
        student =   buildStudent("Matt");
        course =   buildCourse("Math", LocalDate.now(), LocalDate.now(),10, 10);
        cre = new CourseRegistrationEntity();
        cre.setCourse(course);
        cre.setStudent(student);
        cre.setRegistrationDate(LocalDate.now());
    }


    @Test
    public void testCourseRegistrationDetails() throws Exception {
        assertThat(cre.getStudent().getName()).isEqualTo(EXPECTED_STUDENT_NAME);
        assertThat(cre.getCourse().getTitle()).isEqualTo(EXPECTED_COURSE_TITLE);
        assertThat(cre.getRegistrationDate()).isEqualTo(EXPECTED_REGISTRATION_DATE);
    }
}

