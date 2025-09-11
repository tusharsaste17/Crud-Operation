package com.project.test.repo;

import com.project.test.entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<StudentDetails, Integer> {

}
