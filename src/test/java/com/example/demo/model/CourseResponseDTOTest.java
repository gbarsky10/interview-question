package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.example.demo.TestHelper.buildCourseRequestDTO;
import static com.example.demo.TestHelper.buildCourseResponseDTO;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseResponseDTOTest {
    public static final String EXPECTED_TITLE = "Math";
    public static final LocalDate EXPECTED_START_DATE = LocalDate.now();
    public static final int EXPECTED_CAPACITY = 10;
    public static final LocalDate EXPECTED_END_DATE = LocalDate.now();
    public static final int EXPECTED_REMAINING = 10;
    public static final int EXPECTED_PARTICIPANTS_SIZE = 1;
    private CourseResponseDTO crDTO;

    @BeforeEach
    public void setUp() throws Exception {
        StudentDTO student = new StudentDTO(LocalDate.now(), "Matt");
        Set<StudentDTO> participants = new HashSet<>();
        participants.add(student);
        crDTO = buildCourseResponseDTO(1l, "Math", LocalDate.now(), LocalDate.now(), 10, 10, participants);
    }

    @Test
    public void testCourseResponseDTODetails() throws Exception {
        assertThat(crDTO.getTitle()).isEqualTo(EXPECTED_TITLE);
        assertThat(crDTO.getStartDate()).isEqualTo(EXPECTED_START_DATE);
        assertThat(crDTO.getEndDate()).isEqualTo(EXPECTED_END_DATE);
        assertThat(crDTO.getCapacity()).isEqualTo(EXPECTED_CAPACITY);
        assertThat(crDTO.getRemaining()).isEqualTo(EXPECTED_REMAINING);
        assertThat(crDTO.getParticipants().size()).isEqualTo(EXPECTED_PARTICIPANTS_SIZE);

    }

}