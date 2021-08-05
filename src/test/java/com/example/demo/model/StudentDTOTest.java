package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.example.demo.TestHelper.buildCourseResponseDTO;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentDTOTest {
    public static final String EXPECTED_NAME = "Matt";
    public static final LocalDate EXPECTED_DATE = LocalDate.now();
    private StudentDTO studentDTO;

    @BeforeEach
    public void setUp() throws Exception {
        studentDTO = new StudentDTO(LocalDate.now(), "Matt");
    }

    @Test
    public void testStudentDTODetails() throws Exception {
        assertThat(studentDTO.getName()).isEqualTo(EXPECTED_NAME);
        assertThat(studentDTO.getRegistrationDate()).isEqualTo(EXPECTED_DATE);
    }
}
