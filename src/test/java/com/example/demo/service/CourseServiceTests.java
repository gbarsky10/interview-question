package com.example.demo.service;

import com.example.demo.model.CourseRequestDTO;
import com.example.demo.model.CourseResponseDTO;
import com.example.demo.model.StudentCancellationRequestDTO;
import com.example.demo.model.StudentEnrollmentRequestDTO;
import com.example.demo.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.example.demo.TestHelper.*;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTests {

    @Mock
    private CourseRepository courseRepositoryMock;

    @Mock
    private StudentRepository studentRepositoryMock;

    @Mock
    private CourseRegistrationRepository courseRegistrationRepositoryMock;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void initUseCase() {

    }

    @Test
    public void addCourse_Success() {

        CourseEntity course  = buildCourse("Math",LocalDate.now().plusDays(5),LocalDate.now().plusDays(10),5, 5 );
        CourseRequestDTO courseDTO = buildCourseRequestDTO("Math",LocalDate.now().plusDays(5),LocalDate.now().plusDays(10),5);
        // providing knowledge
        when(courseRepositoryMock.save(any(CourseEntity.class))).thenReturn(course);

        CourseResponseDTO addedCourse = courseService.addCourse(courseDTO);
        assertThat(addedCourse.getTitle()).isEqualTo("Math");
    }

    @Test
    public void findCourse_by_id_Success() {

        CourseEntity course  = buildCourse("Math",LocalDate.now().plusDays(5),LocalDate.now().plusDays(10),5, 5 );
        CourseRequestDTO courseDTO = buildCourseRequestDTO("Math",LocalDate.now().plusDays(5),LocalDate.now().plusDays(10),5);
        // providing knowledge
        course.setId(1l);
        when(courseRepositoryMock.save(any(CourseEntity.class))).thenReturn(course);
        when(courseRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(course));

        CourseResponseDTO addedCourse = courseService.addCourse(courseDTO);
        CourseResponseDTO foundCourse = courseService.findCourseById(1l);
        assertThat(foundCourse.getTitle()).isEqualTo("Math");
    }

    @Test
    public void findCourse_by_title_Success() {

        CourseEntity course  = buildCourse("Math",LocalDate.now().plusDays(5),LocalDate.now().plusDays(10),5, 5 );
        CourseRequestDTO courseDTO = buildCourseRequestDTO("Math",LocalDate.now().plusDays(5),LocalDate.now().plusDays(10),5);
        List<CourseEntity> courses = new ArrayList<>();
        courses.add(course);
        // providing knowledge
        course.setId(1l);
        when(courseRepositoryMock.save(any(CourseEntity.class))).thenReturn(course);
        when(courseRepositoryMock.findByTitle(any(String.class))).thenReturn(courses);

        CourseResponseDTO addedCourse = courseService.addCourse(courseDTO);
        List<CourseResponseDTO> foundCourses = courseService.findCoursesByTitle("Math");
        assertThat(foundCourses.size()).isEqualTo(1);
    }

    @Test
    public void enrollStudent_Success() {

        CourseEntity course  = buildCourse("Math",LocalDate.now().plusDays(5),LocalDate.now().plusDays(10),5, 5 );
        course.setId(1l);

        StudentEntity student = buildStudent("Matt");
        student.setId(1l);

        CourseRegistrationEntity cr = new CourseRegistrationEntity();
        cr.setStudent(student);
        cr.setCourse(course);
        cr.setRegistrationDate(LocalDate.now());

        CourseRequestDTO courseDTO = buildCourseRequestDTO("Math",LocalDate.now().plusDays(5),LocalDate.now().plusDays(10),5);
        StudentEnrollmentRequestDTO ser = buildStudentEnrollmentRequest("Matt", LocalDate.now());
        // providing knowledge

        when(courseRepositoryMock.save(any(CourseEntity.class))).thenReturn(course);
        when(studentRepositoryMock.save(any(StudentEntity.class))).thenReturn(student);
        when(courseRegistrationRepositoryMock.save(any(CourseRegistrationEntity.class))).thenReturn(cr);
        when(courseRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(course));

        CourseResponseDTO addedCourse = courseService.addCourse(courseDTO);
        addedCourse = courseService.enrollStudentToCourse(1l,ser);

        assertThat(addedCourse.getParticipants().size()).isEqualTo(1);
    }

    @Test
    public void cancelStudent_Success() {

        CourseEntity course  = buildCourse("Math",LocalDate.now().plusDays(5),LocalDate.now().plusDays(10),5, 5 );
        course.setId(1l);

        StudentEntity student = buildStudent("Matt");
        student.setId(1l);

        CourseRegistrationEntity cr = new CourseRegistrationEntity();
        cr.setStudent(student);
        cr.setCourse(course);
        cr.setRegistrationDate(LocalDate.now());

        CourseRequestDTO courseDTO = buildCourseRequestDTO("Math",LocalDate.now().plusDays(5),LocalDate.now().plusDays(10),5);
        StudentEnrollmentRequestDTO ser = buildStudentEnrollmentRequest("Matt", LocalDate.now());
        StudentCancellationRequestDTO csr = buildStudentCancelletionRequest("Matt", LocalDate.now());
        // providing knowledge

        when(courseRepositoryMock.save(any(CourseEntity.class))).thenReturn(course);
        when(studentRepositoryMock.save(any(StudentEntity.class))).thenReturn(student);
        when(courseRegistrationRepositoryMock.save(any(CourseRegistrationEntity.class))).thenReturn(cr);
        when(courseRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(course));

        CourseResponseDTO addedCourse = courseService.addCourse(courseDTO);
        addedCourse = courseService.enrollStudentToCourse(1l,ser);
        addedCourse = courseService.cancelStudentEnrollment(1l,csr);

        assertThat(addedCourse.getParticipants().size()).isEqualTo(1);
    }

}
