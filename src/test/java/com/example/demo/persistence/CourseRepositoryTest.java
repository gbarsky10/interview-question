package com.example.demo.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.demo.TestHelper.buildCourse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
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
    void initUseCase() {
        // CourseEntity courseEntity = buildCourse("Comp Science", now.plusDays(5), now.plusDays(10), 5,5);
        //courseEntity.setId(1l);
        // existingCourse = courseRepository.save(courseEntity);
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

        CourseEntity courseEntity = buildCourse("Comp Science", now.plusDays(5), now.plusDays(10), 5, 5);
        existingCourse = courseRepository.save(courseEntity);
        Optional<CourseEntity> optionalCourse = courseRepository.findById(1l);
        assertThat(optionalCourse.get().getTitle()).isEqualTo("Comp Science");

    }

    @Test
    void find_by_title__given_existing_course__then_course_found() {
        CourseEntity courseEntity = buildCourse("Comp Science", now.plusDays(5), now.plusDays(10), 5, 5);
        existingCourse = courseRepository.save(courseEntity);
        List<CourseEntity> courses = courseRepository.findByTitle("Science");
        assertThat(courses.size()).isEqualTo(1);
    }


    @Test
    void find_by_name__given_existing_student__then_student_found() {
        givenExistingStudent("Developer");

        whenFindByName("Developer");

        thenAssertStudentFoundByName();
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
}
