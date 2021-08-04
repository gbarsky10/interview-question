package com.example.demo.service.rest;

import com.example.demo.model.CourseRequestDTO;
import com.example.demo.model.CourseResponseDTO;
import com.example.demo.model.StudentCancellationRequestDTO;
import com.example.demo.model.StudentEnrollmentRequestDTO;
import com.example.demo.persistence.CourseEntity;
import com.example.demo.persistence.StudentEntity;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Course Reservation", description = "Course Reservation API")
@RequestMapping("/courses")
public interface RestService {

    @Operation(summary = "Search course by title", responses = {
            @ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CourseResponseDTO>> getCoursesByTitle(
            @RequestParam(name = "title", required = true)  String title);

    @Operation(summary = "Get Course Details", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found: course for a given course ID does not exist")})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CourseResponseDTO> getCourse(@PathVariable() Long id);

    @Operation(summary = "Add new course", responses = {
            @ApiResponse(responseCode = "201", description = "Created: new course was added"),
            @ApiResponse(responseCode = "400", description = "Bad request: new course was not added")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CourseResponseDTO> addCourse(@RequestBody() @Valid CourseRequestDTO course);

    @Operation(summary = "Sign up for course", responses = {
            @ApiResponse(responseCode = "200", description = "Created: new user signed up for a course"),
            @ApiResponse(responseCode = "400", description = "Bad request: new user was not signed up"),
            @ApiResponse(responseCode = "404", description = "Bad request: course does not exist")})
    @PostMapping(value = "/{id}/add", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CourseResponseDTO> addStudentToCourse(@PathVariable() Long id, @RequestBody() @Valid StudentEnrollmentRequestDTO studentEnrollment);

    @Operation(summary = "Cancel user enrollment", responses = {
            @ApiResponse(responseCode = "200", description = "Success: enrollment was cancelled"),
            @ApiResponse(responseCode = "400", description = "Bad request: cancel data is wrong"),
            @ApiResponse(responseCode = "404", description = "Not found: course does not exist or user is not enrolled")})
    @PostMapping(value = "/{id}/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CourseResponseDTO> cancelUserRegistration (@PathVariable("id") Long id, @RequestBody() StudentCancellationRequestDTO studentCancellation);
}
