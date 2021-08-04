package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository
        extends JpaRepository<CourseEntity, Long> {

    @Query(value = "select * from course c where c.title like %?1%", nativeQuery = true)
    public List<CourseEntity> findByTitle(String courseTitle);
}

