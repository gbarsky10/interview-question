package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.example.demo.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseRequestDTOTest {
    public static final String EXPECTED_TITLE = "Math";
    public static final LocalDate EXPECTED_START_DATE = LocalDate.now();
    public static final int EXPECTED_CAPACITY = 10;
    public static final LocalDate EXPECTED_END_DATE = LocalDate.now();
    private CourseRequestDTO crDTO;

    @BeforeEach
    public void setUp() throws Exception {
        crDTO = buildCourseRequestDTO("Math", LocalDate.now(), LocalDate.now(), 10);

    }

    @Test
    public void testCourseRequestDTODetails() throws Exception {
        assertThat(crDTO.getTitle()).isEqualTo(EXPECTED_TITLE);
        assertThat(crDTO.getStartDate()).isEqualTo(EXPECTED_START_DATE);
        assertThat(crDTO.getEndDate()).isEqualTo(EXPECTED_END_DATE);
        assertThat(crDTO.getCapacity()).isEqualTo(EXPECTED_CAPACITY);
    }
}

