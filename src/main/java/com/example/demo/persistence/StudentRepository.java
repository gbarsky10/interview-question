package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StudentRepository
        extends JpaRepository<StudentEntity, Long> {

    @Query(value = "select * from student s where s.name = ?1", nativeQuery = true)
    public Optional<StudentEntity> findByName(String studentName);
}

