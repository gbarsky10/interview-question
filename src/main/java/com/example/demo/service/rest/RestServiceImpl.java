
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

    /**
     * Get list of Courses by Title
     * @param title
     * @return
     */
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByTitle(String title) {
        List<CourseResponseDTO> courses = courseService.findCoursesByTitle(title);
        return new ResponseEntity<List<CourseResponseDTO>>(courses, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Get Course Details by ID
     * @param id
     * @return
     */
    public ResponseEntity<CourseResponseDTO> getCourse(Long id) {
        CourseResponseDTO course = courseService.findCourseById(id);
        return new ResponseEntity<CourseResponseDTO>(course, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Add new course
     * @param course
     * @return
     */
    public ResponseEntity<CourseResponseDTO> addCourse(CourseRequestDTO course) {
        CourseResponseDTO newCourse = courseService.addCourse(course);
        return new ResponseEntity<CourseResponseDTO>(newCourse, new HttpHeaders(), HttpStatus.CREATED);
    }

    /**
     * Enroll student to a given course
     * @param id
     * @param studentEnrollment
     * @return
     */
    public ResponseEntity<CourseResponseDTO> addStudentToCourse(Long id, StudentEnrollmentRequestDTO studentEnrollment) {
        CourseResponseDTO course = courseService.enrollStudentToCourse(id, studentEnrollment);
        return new ResponseEntity<CourseResponseDTO>(course, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Remove student from a given course
     * @param id
     * @param studentCancellation
     * @return
     */
    public ResponseEntity<CourseResponseDTO> cancelUserRegistration(Long id, StudentCancellationRequestDTO studentCancellation) {
        CourseResponseDTO course = courseService.cancelStudentEnrollment(id, studentCancellation);
        return new ResponseEntity<CourseResponseDTO>(course, new HttpHeaders(), HttpStatus.OK);
    }

}