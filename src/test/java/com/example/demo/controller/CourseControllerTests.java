package com.example.demo.controller;

import com.example.demo.model.CourseRequestDTO;
import com.example.demo.model.CourseResponseDTO;
import com.example.demo.model.ErrorResponse;
import com.example.demo.persistence.CourseRepository;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static com.example.demo.TestHelper.buildCourseRequestDTO;
import static com.example.demo.TestHelper.buildStudentCancelletionRequest;
import static com.example.demo.TestHelper.buildStudentEnrollmentRequest;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static com.example.demo.TestHelper.buildCourseResponseDTO;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerTests {

    @Autowired
    CourseRepository courseRepository;

    @LocalServerPort
    int port;

    String controllerPath = "/courses";
    CourseResponseDTO existingCourseResponseDTO;
    ErrorResponse errorResponse;
    ResponseEntity<CourseResponseDTO> successResponse;
    int statusCode;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;

        existingCourseResponseDTO = null;
        errorResponse = null;
        successResponse = null;

        courseRepository.deleteAll();

    }

    @Nested
    class Get_Course_Details {

        CourseResponseDTO foundCourseDTO;

        @BeforeEach
        void beforeEach() {
            foundCourseDTO = null;
        }

        @Test
        void given_non_existing_course_id__then_status_not_found() {
            given()
                    .pathParam("id", 10)
                    .when().get(controllerPath + "/{id}")
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void given_existing_course_id__then_course_found() {
            givenExistingCourse("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10));

            whenGetCourseDetails();

            thenAssertCourseFound();
        }

        private void whenGetCourseDetails() {
            foundCourseDTO = given().pathParam("id", 1)
                    .when().get(controllerPath + "/{id}")
                    .as(CourseResponseDTO.class);
        }

        private void thenAssertCourseFound() {
            assertThat(foundCourseDTO).isEqualTo(existingCourseResponseDTO);
        }
    }

    @Nested
    class Get_Courses_By_Title {

        List<CourseResponseDTO> foundCoursesDTO;

        @BeforeEach
        void beforeEach() {
            foundCoursesDTO = null;
        }

        @Test
        void given_non_existing_course_title__then_status_not_found() {
            given()
                    //.queryParam("title", "Math")
                    .when().get(controllerPath + "/title=Math")
                    .then().statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void given_existing_course_title__then_course_found() {
            givenExistingCourse("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10));

            whenGetCoursesByTitle();

            thenAssertCourseFound();
        }

        private void whenGetCoursesByTitle() {
            foundCoursesDTO = (List<CourseResponseDTO>) given()
                    .when().get(controllerPath + "/title=Math");

        }

        private void thenAssertCourseFound() {
            assertThat(foundCoursesDTO.size() == 1);
        }
    }

    @Nested
    class Enroll_Student_To_Course {
        CourseResponseDTO actualCourseDetails;

        @BeforeEach
        void beforeEach() {
            actualCourseDetails = null;
        }

        @Test
        void given_course__enroll_student__enrollment_successful() {
            givenExistingCourse("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10));
            whenAddStudentToCourse("Matt", LocalDate.now());
            assertThat(actualCourseDetails.getParticipants().size() == 1);
        }

        @Test
        void given_course__enroll_student_less_three_days__enrollment_not_successful() {
            givenExistingCourse("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10));
            whenAddStudentWithInvalidRegistrationDate("Matt", LocalDate.now().plusDays(3));
            thenAssertApiErrorThrown(HttpStatus.BAD_REQUEST);
        }

        @Test
        void given_course__enroll_student_when_course_is_full__enrollment_not_successful() {
            givenExistingCourseWithZeroRemaining("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),0);
            whenAddStudentToCourse("Matt", LocalDate.now());
            thenAssertApiErrorThrown(HttpStatus.BAD_REQUEST);
        }

        @Test
        void given_course__enroll_student_with_same_name__enrollment_not_successful() {
            givenExistingCourseWithZeroRemaining("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),0);
            whenAddStudentsWithSameName("Matt", LocalDate.now().plusDays(3));
            thenAssertApiErrorThrown(HttpStatus.BAD_REQUEST);
        }

        private void whenAddStudentToCourse(String name, LocalDate registrationDate) {
            actualCourseDetails = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildStudentEnrollmentRequest(name,registrationDate))
                    .when().post(controllerPath + "/1/add")
                    .as(CourseResponseDTO.class);
        }

        private void whenAddStudentWithInvalidRegistrationDate(String name, LocalDate registrationDate){
            errorResponse = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildStudentEnrollmentRequest(name,registrationDate))
                    .when().post(controllerPath + "/1/add")
                    .as(ErrorResponse.class);
        }

        private void whenAddStudentsWithSameName(String name, LocalDate registrationDate) {
            CourseResponseDTO firstStudentEnrollmentResult = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildStudentEnrollmentRequest(name,registrationDate))
                    .when().post(controllerPath + "/1/add")
                    .as(CourseResponseDTO.class);

            errorResponse = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildStudentEnrollmentRequest(name,registrationDate))
                    .when().post(controllerPath + "/1/add")
                    .as(ErrorResponse.class);

        }
    }

    @Nested
    class Remove_Student_From_Course {
        CourseResponseDTO actualCourseDetails;

        @BeforeEach
        void beforeEach() {
            actualCourseDetails = null;
        }

        @Test
        void given_course__remove_student__cancel_successful() {
            givenExistingCourse("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10));
            whenRemoveStudentFromCourse("Matt", LocalDate.now().plusDays(1), LocalDate.now());
            assertThat(actualCourseDetails.getParticipants().size() == 0);
        }

        @Test
        void given_course__remove_student_less_three_days__cancel_not_successful() {
            givenExistingCourse("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10));
            whenRemoveStudentWithInvalidCancelDate("Matt",LocalDate.now(),  LocalDate.now().plusDays(3));
            thenAssertApiErrorThrown(HttpStatus.BAD_REQUEST);
        }

        @Test
        void given_course__remove_student_who_is_not_registered__cancel_not_successful() {
            givenExistingCourseWithZeroRemaining("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),0);
            whenRemoveWrongStudent("Matt", "Daniel", LocalDate.now(), LocalDate.now().plusDays(3) );
            thenAssertApiErrorThrown(HttpStatus.BAD_REQUEST);
        }

        private void whenRemoveStudentFromCourse(String name, LocalDate registrationDate, LocalDate cancelDate) {
            CourseResponseDTO studentEnrollment = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildStudentEnrollmentRequest(name,registrationDate))
                    .when().post(controllerPath + "/1/add")
                    .as(CourseResponseDTO.class);

            actualCourseDetails = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildStudentCancelletionRequest(name,cancelDate))
                    .when().post(controllerPath + "/1/remove")
                    .as(CourseResponseDTO.class);
        }

        private void whenRemoveStudentWithInvalidCancelDate(String name, LocalDate registrationDate, LocalDate cancelDate){
            CourseResponseDTO studentEnrollment = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildStudentEnrollmentRequest(name,registrationDate))
                    .when().post(controllerPath + "/1/add")
                    .as(CourseResponseDTO.class);

            errorResponse = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildStudentCancelletionRequest(name,cancelDate))
                    .when().post(controllerPath + "/1/remove")
                    .as(ErrorResponse.class);
        }
        private void whenRemoveWrongStudent(String name1, String name2, LocalDate registrationDate, LocalDate cancelDate){
            CourseResponseDTO studentEnrollment = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildStudentEnrollmentRequest(name1,registrationDate))
                    .when().post(controllerPath + "/1/add")
                    .as(CourseResponseDTO.class);

            errorResponse = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildStudentCancelletionRequest(name2,cancelDate))
                    .when().post(controllerPath + "/1/remove")
                    .as(ErrorResponse.class);
        }
    }
    @Nested
    class Add_Course {

        CourseRequestDTO newCourseRequest;

        @BeforeEach
        void beforeEach() {
            newCourseRequest = null;
        }

        @Test
        void given_course__then_status_success() {
            givenExistingCourse("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10));

            whenAddCourseSuccessResult("Math", LocalDate.now().plusDays(5), LocalDate.now().plusDays(10));

            thenAssertApiSuccessStatus(HttpStatus.CREATED.value());
        }

        private void whenAddCourseSuccessResult(String title, LocalDate startDate, LocalDate endDate) {

           // ResponseEntity<CourseResponseDTO> successResponse; //=  ResponseEntity<CourseResponseDTO>(null, new HttpHeaders(), HttpStatus.OK)
            //CourseResponseDTO courseResponseDTO = buildCourseResponseDTO(1l,title,startDate,endDate,10,10,null);
            //ResponseEntity<CourseResponseDTO> res = new ResponseEntity<CourseResponseDTO>(courseResponseDTO, new HttpHeaders(), HttpStatus.CREATED);
            statusCode = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(buildCourseRequestDTO(title,startDate, endDate,10))
                    .when().post(controllerPath).getStatusCode();
                    //.as(ResponseEntity.class);
        }
    }

    private void givenExistingCourse(String title, LocalDate startDate, LocalDate endDate) {
        existingCourseResponseDTO = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(buildCourseRequestDTO(title,startDate, endDate,10))
                .when().post(controllerPath)
                .as(CourseResponseDTO.class);
        assumeThat(existingCourseResponseDTO.getId()).isEqualTo(1l);

    }
    private void givenExistingCourseWithZeroRemaining(String title, LocalDate startDate, LocalDate endDate, int remaining) {
        existingCourseResponseDTO = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(buildCourseRequestDTO(title,startDate, endDate,0))
                .when().post(controllerPath)
                .as(CourseResponseDTO.class);
        assumeThat(existingCourseResponseDTO.getId()).isEqualTo(1l);

    }

    private void thenAssertApiSuccessStatus(int expectedHttpStatusCode) {
        assertThat(statusCode).isEqualTo(expectedHttpStatusCode);
    }

    private void thenAssertApiErrorThrown(HttpStatus expectedHttpStatus) {
        assertThat(errorResponse.getStatus()).isEqualTo(expectedHttpStatus);
    }
}
