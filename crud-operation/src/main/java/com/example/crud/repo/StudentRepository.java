package com.example.crud.repo;

import com.example.crud.entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentDetails, Long> {
}
