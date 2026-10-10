package com.campus.management.repository;

import com.campus.management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNumber(String rollNumber);
    Optional<Student> findByUserId(Long userId);
    long countByAttendancePercentageLessThan(Integer threshold);
}
