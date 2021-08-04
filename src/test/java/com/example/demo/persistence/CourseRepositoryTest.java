package com.example.demo.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.demo.TestHelper.buildCourse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;

    CourseEntity existingCourse;
    Optional<CourseEntity> courseOptionalForId;
    List<CourseEntity> coursesByTitle;
    StudentEntity existingStudent;
    Optional<StudentEntity> studentOptionalByName;

    LocalDate now;

    @BeforeEach
    void beforeEach() {
        courseRepository.deleteAll();
        studentRepository.deleteAll();
        now = LocalDate.now();
        existingCourse = null;
        existingStudent = null;
        courseOptionalForId = null;
        coursesByTitle = null;
        studentOptionalByName = null;
    }

    @Test
    void find_by_id__given_existing_course__then_course_found() {
        givenExistingCourse("Comp Science", now.plusDays(5), now.plusDays(10), 5);

        whenFindById();

        thenAssertCourseFoundForId();
    }

    @Test
    void find_by_title__given_existing_course__then_course_found() {
        givenExistingCourse("Comp Science", now.plusDays(5), now.plusDays(10), 5);

        whenFindByTitle("Science");

        thenAssertCourseFoundByTitle();
    }


    @Test
    void find_by_name__given_existing_student__then_student_found() {
        givenExistingStudent("Developer");

        whenFindByName("Developer");

        thenAssertStudentFoundByName();
    }


    private void givenExistingCourse(String title, LocalDate startDate, LocalDate endDate, int capacity) {
        CourseEntity courseEntity = buildCourse(title, startDate, endDate, capacity, capacity);
        existingCourse = courseRepository.save(courseEntity);
    }

    private void whenFindById() {
        courseOptionalForId = courseRepository.findById(1l);
    }

    private void whenFindByTitle(String title) {
        coursesByTitle = courseRepository.findByTitle(title);
    }

    private void thenAssertCourseFoundForId() {
        assertAll("foundCourse",
                () -> assertThat(courseOptionalForId).hasValue(existingCourse));
    }

    private void givenExistingStudent(String name) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(name);
        existingStudent = studentRepository.save(studentEntity);
    }

    private void whenFindByName(String name) {
        studentOptionalByName = studentRepository.findByName(name);
    }

    private void thenAssertStudentFoundByName() {
        assertAll("foundStudent",
                () -> assertThat(studentOptionalByName).hasValue(existingStudent));
    }
    private void thenAssertCourseFoundByTitle() {
        assertAll(
            () -> assertThat(coursesByTitle).size().isEqualTo(1),
            () -> assertThat(existingCourse).isIn(coursesByTitle));
    }
}
