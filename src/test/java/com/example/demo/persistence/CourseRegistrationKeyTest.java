package com.example.demo.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseRegistrationKeyTest {

    public static final Long EXPECTED_STUDENT_ID = 10l;
    public static final Long EXPECTED_COURSE_ID = 1L;
    private CourseRegistrationKey crk;

    @BeforeEach
    public void setUp() throws Exception {
        crk = new CourseRegistrationKey();
        crk.setCourseId(1l);
        crk.setStudentId(10l);
    }


    @Test
    public void testCourseRegistrationKeyDetails() throws Exception {
        assertThat(crk.getCourseId()).isEqualTo(EXPECTED_COURSE_ID);
        assertThat(crk.getStudentId()).isEqualTo(EXPECTED_STUDENT_ID);
    }
}
