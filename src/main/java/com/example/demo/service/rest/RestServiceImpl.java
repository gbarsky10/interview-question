
package com.example.demo.service.rest;

import com.example.demo.model.CourseRequestDTO;
import com.example.demo.model.CourseResponseDTO;
import com.example.demo.model.StudentCancellationRequestDTO;
import com.example.demo.model.StudentEnrollmentRequestDTO;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class RestServiceImpl implements RestService {

    private CourseService courseService;

    @Autowired
    public RestServiceImpl(CourseService courseService) {
        this.courseService = courseService;
    }

    public ResponseEntity<List<CourseResponseDTO>> getCoursesByTitle(String title) {
        List<CourseResponseDTO> courses = courseService.findCoursesByTitle(title);
        return new ResponseEntity<List<CourseResponseDTO>>(courses, new HttpHeaders(), HttpStatus.OK);
    }


    public ResponseEntity<CourseResponseDTO> getCourse(Long id) {
        CourseResponseDTO course = courseService.findCourseById(id);
        return new ResponseEntity<CourseResponseDTO>(course, new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<CourseResponseDTO> addCourse(CourseRequestDTO course) {
        CourseResponseDTO newCourse = courseService.addCourse(course);
        return new ResponseEntity<CourseResponseDTO>(newCourse, new HttpHeaders(), HttpStatus.CREATED);
    }

    public ResponseEntity<CourseResponseDTO> addStudentToCourse(Long id, StudentEnrollmentRequestDTO studentEnrollment) {
        CourseResponseDTO course = courseService.enrollStudentToCourse(id, studentEnrollment);
        return new ResponseEntity<CourseResponseDTO>(course, new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<CourseResponseDTO> cancelUserRegistration(Long id, StudentCancellationRequestDTO studentCancellation) {
        CourseResponseDTO course = courseService.cancelStudentEnrollment(id, studentCancellation);
        return new ResponseEntity<CourseResponseDTO>(course, new HttpHeaders(), HttpStatus.OK);
    }

}