package com.example.demo.persistence;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity domain object representing a course.
 */


@Getter @Setter
@EqualsAndHashCode(exclude = "registrations")
@Entity
@Table(name="course")
public class CourseEntity implements Serializable {


    private static final long serialVersionUID = 8842434102002921128L;

    /**
     * Persistence ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "remainingPlaces", nullable = false)
    private int remaining;

    @OneToMany(mappedBy = "course")
    Set<CourseRegistrationEntity> registrations = new HashSet<>();;

    @Override
    public String toString() {
        return "Course [courseId=" + id + " courseTitle=" + title + " startDate=" + startDate.toString() + " endDate=" + endDate.toString() + " capacity=" + capacity + "]";
    }
}
