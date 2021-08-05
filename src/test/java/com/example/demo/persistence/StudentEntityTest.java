package com.example.demo.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.example.demo.TestHelper.buildStudent;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentEntityTest {

    public static final String EXPECTED_NAME = "Matt";
    private StudentEntity student;

    @BeforeEach
    public void setUp() throws Exception {
        student =   buildStudent("Matt");
    }


    @Test
    public void testStudentDetails() throws Exception {
        assertThat(student.getName()).isEqualTo(EXPECTED_NAME);
    }
}

