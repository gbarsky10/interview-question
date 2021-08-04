package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.persistence.CourseEntity;

import java.time.LocalDate;
import java.util.Set;

public class TestHelper {

    public static final String COURSE_TITLE = "Computer Science";
    public static final int DEFAULT_CAPACITY = 3;

    public static CourseRequestDTO buildCourseRequestDTO(LocalDate startDate, LocalDate endDate) {
        return buildCourseRequestDTO(COURSE_TITLE, startDate, endDate,DEFAULT_CAPACITY);
    }

    public static CourseRequestDTO buildCourseRequestDTO(String courseTitle, LocalDate startDate, LocalDate endDate, int capacity){
        return CourseRequestDTO.builder()
                .title(courseTitle)
                .startDate(startDate)
                .endDate(endDate)
                .capacity(capacity)
                .build();
    }

    public static CourseResponseDTO buildCourseResponseDTO(Long id, String courseTitle, LocalDate startDate, LocalDate endDate, int capacity, int remaining, Set<StudentDTO> students){

        CourseResponseDTO response = new CourseResponseDTO();
        response.setId(id);
        response.setTitle(courseTitle);
        response.setStartDate(startDate);
        response.setEndDate(endDate);
        response.setCapacity(capacity);
        response.setRemaining(remaining);
        response.setParticipants(students);

        return response;
    }

    public static CourseEntity buildCourse(String title, LocalDate startDate, LocalDate endDate, int capacity, int remaining) {

        CourseEntity newCourse  = new CourseEntity();
        newCourse.setTitle(title);
        newCourse.setStartDate(startDate);
        newCourse.setEndDate(endDate);
        newCourse.setCapacity(capacity);
        newCourse.setRemaining(remaining);

        return newCourse;

    }

    public static StudentEnrollmentRequestDTO buildStudentEnrollmentRequest(String name, LocalDate registrationDate) {
        StudentEnrollmentRequestDTO ser = new StudentEnrollmentRequestDTO();
        ser.setName(name);
        ser.setRegistrationDate(registrationDate);
        ser.setCourseId(1l);
        return ser;
    }

    public static StudentCancellationRequestDTO buildStudentCancelletionRequest(String name, LocalDate cancelDate) {
        StudentCancellationRequestDTO studentCancellationRequestDTO = new StudentCancellationRequestDTO();
        studentCancellationRequestDTO.setName(name);
        studentCancellationRequestDTO.setCancelDate(cancelDate);
        studentCancellationRequestDTO.setCourseId(1l);
        return studentCancellationRequestDTO;
    }
}
