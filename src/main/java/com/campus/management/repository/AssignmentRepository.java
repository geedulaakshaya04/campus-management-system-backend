package com.campus.management.repository;

import com.campus.management.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByBranchAndSemester(String branch, Integer semester);
}
