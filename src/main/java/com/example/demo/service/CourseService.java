package com.example.demo.service;

import com.example.demo.model.CourseRequestDTO;
import com.example.demo.model.CourseResponseDTO;
import com.example.demo.model.StudentCancellationRequestDTO;
import com.example.demo.model.StudentEnrollmentRequestDTO;
import java.util.List;

public interface CourseService {

    List<CourseResponseDTO> findCoursesByTitle(String title);

    CourseResponseDTO findCourseById(Long id);

    CourseResponseDTO addCourse(CourseRequestDTO course);

    CourseResponseDTO enrollStudentToCourse(Long id, StudentEnrollmentRequestDTO studentEnrollment);

    CourseResponseDTO cancelStudentEnrollment(Long id, StudentCancellationRequestDTO studentEnrollment);

}
