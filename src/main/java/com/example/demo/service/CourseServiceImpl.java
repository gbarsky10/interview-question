package com.example.demo.service;

import com.example.demo.exception.*;
import com.example.demo.model.*;
import com.example.demo.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private CourseRegistrationRepository courseRegistrationRepository;


    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, CourseRegistrationRepository courseRegistrationRepository)
    {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    /**
     * @method:  findCoursesByTitle
     * @description: Implements the logic of retrieving courses by title
     * @param title
     * @return List of courses
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourseResponseDTO> findCoursesByTitle(String title){

        List<CourseResponseDTO> courseList = new ArrayList<CourseResponseDTO>();
        courseRepository.findByTitle(title).forEach(course -> {
            CourseResponseDTO courseDto = courseEntityToDTO(course);
            Set<CourseRegistrationEntity> registrations = course.getRegistrations();
            Set<StudentDTO> studentsDTO = new HashSet<StudentDTO>();
            for (CourseRegistrationEntity re : registrations) {
                StudentEntity se = re.getStudent();
                StudentDTO studentDTO = studentEntityToDTO(se, re.getRegistrationDate());
                studentsDTO.add(studentDTO);
            }
            courseDto.setParticipants(studentsDTO);
            courseList.add(courseDto);
        });

        return courseList;
    }

    /**
     * @method: findCourseById
     * @description: Retrieve course details based on a give course ID
     * @param id  Long
     * @return Course details
     */
    @Override
    @Transactional(readOnly = true)
    public CourseResponseDTO findCourseById(Long id) {

        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException(String.format("Course was not found for id=%s", id));
        }
        CourseEntity ce  = course.get();
        Set<CourseRegistrationEntity> registrations = ce.getRegistrations();
        CourseResponseDTO courseDto = courseEntityToDTO(ce);
        Set<StudentDTO> students = new HashSet<StudentDTO>();
        for (CourseRegistrationEntity re : registrations) {
            students.add(studentEntityToDTO(re.getStudent(), re.getRegistrationDate()));
        }
        courseDto.setParticipants(students);

        return courseDto;
    }

    /**
     * method: addCourse
     * @description: Implement tha logic of creating / inserting new course into the DB
     * @param newCourseRequest CourseRequestDTO
     * @return new course details  CourseResponseDTO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CourseResponseDTO addCourse(CourseRequestDTO newCourseRequest) {

        CourseEntity newCourse  = new CourseEntity();
        newCourse.setTitle(newCourseRequest.getTitle());
        newCourse.setStartDate(newCourseRequest.getStartDate());
        newCourse.setEndDate(newCourseRequest.getEndDate());
        newCourse.setCapacity(newCourseRequest.getCapacity());
        newCourse.setRemaining(newCourseRequest.getCapacity());

        newCourse = courseRepository.save(newCourse);

        return courseEntityToDTO(newCourse);
    }

    /**
     * @method: enrollStudentToCourse
     * @description: Implements the logic of signing up a student to a given course
     * @param id Long
     * @param studentEnrollment StudentEnrollmentRequestDTO
     * @return CourseResponseDTO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CourseResponseDTO enrollStudentToCourse(Long id, StudentEnrollmentRequestDTO studentEnrollment){

        // validate that a given course exists
        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException(String.format("Course was not found for id=%s", id));
        }

        CourseEntity courseEntity = course.get();
        if (courseEntity.getRemaining() == 0) {
            throw new CourseIsFullException(String.format("Course with id=%s is already full", id));
        }

        StudentEntity studentEntity;
        Optional<StudentEntity> student = studentRepository.findByName(studentEnrollment.getName());
        if (student.isEmpty()){ // no student exists with that name, new student should be created
            StudentEntity newStudentEntity = new StudentEntity();
            newStudentEntity.setName(studentEnrollment.getName());
            studentEntity = studentRepository.save(newStudentEntity);
        }else{ // student with that name already exists in repo
            studentEntity = student.get();
        }

        Set<CourseRegistrationEntity> registrations = courseEntity.getRegistrations();
        List<Long> studentIds = registrations.stream()
                                .map(r -> r.getStudent().getId())
                                .collect(Collectors.toList());

        if (studentIds.contains(studentEntity.getId())) {
            throw new StudentAlreadyEnrolledException(String.format("Student with the name %s is already enrolled to a given course", studentEntity.getName()));
        }

        if (studentEnrollment.getRegistrationDate().isAfter(courseEntity.getStartDate().minusDays(3))){
            throw new InvalidRegistrationDateException(String.format("Registration date is invalid for the course with id=%s", id));
        }

        CourseRegistrationEntity registration = new CourseRegistrationEntity(courseEntity,studentEntity,studentEnrollment.getRegistrationDate());;
        courseEntity.getRegistrations().add(registration);
        //studentEntity.getRegistrations().add(registration);
        courseRegistrationRepository.save(registration);

        courseEntity.setRemaining(courseEntity.getRemaining()-1);
        CourseEntity updatedCourseEntity = courseRepository.save(courseEntity);

        CourseResponseDTO courseDto = courseEntityToDTO(updatedCourseEntity);
        Set<StudentDTO> students = new HashSet<StudentDTO>();
        for (CourseRegistrationEntity re : updatedCourseEntity.getRegistrations()) {
            students.add(studentEntityToDTO(re.getStudent(), re.getRegistrationDate()));
        }
        courseDto.setParticipants(students);
        return courseDto;
    }

    /**
     * @method: cancelStudentEnrollment
     * @description: Implements the logic of removing a student from  a given course
     * @param id Long
     * @param studentCancellation StudentCancellationRequestDTO
     * @return CourseResponseDTO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CourseResponseDTO cancelStudentEnrollment(Long id, StudentCancellationRequestDTO studentCancellation){

        // validate that a given course exists
        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException(String.format("Course was not found for id=%s", id));
        }

        CourseEntity courseEntity = course.get();
        Set<CourseRegistrationEntity> registrations = courseEntity.getRegistrations();
        List<CourseRegistrationEntity> foundRegistrations = registrations.stream()
                .filter(r -> r.getStudent().getName().equals(studentCancellation.getName()))
                .collect(Collectors.toList());
        if (foundRegistrations.isEmpty()) {
            throw new StudentNotFoundException(String.format("Student with the name %s is not enrolled in a given course", studentCancellation.getName()));
        }

        //Since there only one student name per course, the size of found list should be 1 if srudent is found
        CourseRegistrationEntity r = foundRegistrations.get(0);

        if (studentCancellation.getCancelDate().isAfter(courseEntity.getStartDate().minusDays(3))){
            throw new InvalidCancellationDateException(String.format("Cancellation date is invalid for the course with id=%s", id));
        }

        courseRegistrationRepository.delete(r);
        courseEntity.getRegistrations().remove(r);
        courseEntity.setRemaining(courseEntity.getRemaining()+1);

        CourseEntity updatedCourseEntity = courseRepository.save(courseEntity);

        CourseResponseDTO courseDto = courseEntityToDTO(updatedCourseEntity);
        Set<StudentDTO> students = new HashSet<StudentDTO>();
        for (CourseRegistrationEntity re : updatedCourseEntity.getRegistrations()) {
            students.add(studentEntityToDTO(re.getStudent(), re.getRegistrationDate()));
        }
        courseDto.setParticipants(students);
        return courseDto;

    }


    // private methods
    //========================================================================================
    /**
     * @method courseEntityToDTO
     * @description Map Course Entity to Course Response DTO
     * @param course
     * @return CourseResponseDTO
     */
    private CourseResponseDTO courseEntityToDTO(CourseEntity course) {

        CourseResponseDTO response = new CourseResponseDTO();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setStartDate(course.getStartDate());
        response.setEndDate(course.getEndDate());
        response.setCapacity(course.getCapacity());
        response.setRemaining(course.getRemaining());

        return response;
    }

    /**
     * @method:  studentEntityToDTO
     * @decsription Map Student Entity to Student DTO
     * @param se
     * @param registrationDate
     * @return StudentDTO
     */
    private StudentDTO studentEntityToDTO(StudentEntity se, LocalDate registrationDate){
        StudentDTO studentDTO = StudentDTO.builder()
                                .name(se.getName())
                                .registrationDate(registrationDate)
                                .build();
        return studentDTO;
    }

}