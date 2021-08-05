package com.example.demo.controller;

import com.example.demo.model.CourseRequestDTO;
import com.example.demo.model.CourseResponseDTO;
import com.example.demo.model.StudentCancellationRequestDTO;
import com.example.demo.model.StudentEnrollmentRequestDTO;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Course Reservation", description = "Course Reservation API")
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Get list of Courses by Title
     * @param title
     * @return
     */
    @Operation(summary = "Search course by title", responses = {
            @ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CourseResponseDTO>> getCoursesByTitle(
            @RequestParam(name = "title", required = true)  String title){

        List<CourseResponseDTO> courses = courseService.findCoursesByTitle(title);
        return new ResponseEntity<List<CourseResponseDTO>>(courses, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Get Course Details by ID
     * @param id
     * @return
     */
    @Operation(summary = "Get Course Details", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found: course for a given course ID does not exist")})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CourseResponseDTO> getCourse(@PathVariable() Long id){

        CourseResponseDTO course = courseService.findCourseById(id);
        return new ResponseEntity<CourseResponseDTO>(course, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Add new course
     * @param course
     * @return
     */
    @Operation(summary = "Add new course", responses = {
            @ApiResponse(responseCode = "201", description = "Created: new course was added"),
            @ApiResponse(responseCode = "400", description = "Bad request: new course was not added")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CourseResponseDTO> addCourse(@RequestBody() @Valid CourseRequestDTO course){

        CourseResponseDTO newCourse = courseService.addCourse(course);
        return new ResponseEntity<CourseResponseDTO>(newCourse, new HttpHeaders(), HttpStatus.CREATED);
    }

    /**
     * Enroll student to a given course
     * @param id
     * @param studentEnrollment
     * @return
     */
    @Operation(summary = "Sign up for course", responses = {
            @ApiResponse(responseCode = "200", description = "Created: new user signed up for a course"),
            @ApiResponse(responseCode = "400", description = "Bad request: new user was not signed up"),
            @ApiResponse(responseCode = "404", description = "Bad request: course does not exist")})
    @PostMapping(value = "/{id}/add", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CourseResponseDTO> addStudentToCourse(@PathVariable() Long id, @RequestBody() @Valid StudentEnrollmentRequestDTO studentEnrollment){

        CourseResponseDTO course = courseService.enrollStudentToCourse(id, studentEnrollment);
        return new ResponseEntity<CourseResponseDTO>(course, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Remove student from a given course
     * @param id
     * @param studentCancellation
     * @return
     */

    @Operation(summary = "Cancel user enrollment", responses = {
            @ApiResponse(responseCode = "200", description = "Success: enrollment was cancelled"),
            @ApiResponse(responseCode = "400", description = "Bad request: cancel data is wrong"),
            @ApiResponse(responseCode = "404", description = "Not found: course does not exist or user is not enrolled")})
    @PostMapping(value = "/{id}/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CourseResponseDTO> cancelUserRegistration (@PathVariable("id") Long id, @RequestBody() StudentCancellationRequestDTO studentCancellation){

        CourseResponseDTO course = courseService.cancelStudentEnrollment(id, studentCancellation);
        return new ResponseEntity<CourseResponseDTO>(course, new HttpHeaders(), HttpStatus.OK);

    }
}
