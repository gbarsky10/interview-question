package com.example.demo.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.demo.TestHelper.buildCourse;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

public class CourseEntityTest {

    public static final String EXPECTED_TITLE = "Math";
    public static final LocalDate EXPECTED_START_DATE = LocalDate.now();
    public static final int EXPECTED_CAPACITY = 10;
    private CourseEntity course;

    @BeforeEach
    public void setUp() throws Exception {
        course =   buildCourse("Math", LocalDate.now(), LocalDate.now(),10, 10);
    }


    @Test
    public void testCourseDetails() throws Exception {
        assertThat(course.getTitle()).isEqualTo(EXPECTED_TITLE);
        assertThat(course.getStartDate()).isEqualTo(EXPECTED_START_DATE);
        assertThat(course.getCapacity()).isEqualTo(EXPECTED_CAPACITY);
    }
}

