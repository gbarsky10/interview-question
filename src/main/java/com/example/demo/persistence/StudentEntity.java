package com.example.demo.persistence;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity domain object representing a student.
 */

@Getter @Setter
@EqualsAndHashCode(exclude = "registrations")
@Entity
@Table(name="student")
public class StudentEntity implements Serializable {

    private static final long serialVersionUID = 2315512752359923771L;

    /**
     * Persistence ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", unique = true, nullable = false)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "student")
    Set<CourseRegistrationEntity> registrations = new HashSet<>();;

    @Override
    public String toString() {
        return "Student [studentId=" + id + " name=" +  name + "]";
    }
}
