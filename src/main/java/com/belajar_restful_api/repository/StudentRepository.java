package com.belajar_restful_api.repository;

import com.belajar_restful_api.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.id = ?1 and s.name = ?2")
    Optional<Student> findStudentByIdandName(int id, String name);
}