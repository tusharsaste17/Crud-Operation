package com.project.test.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.test.entity.EmployeeDetail;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeDetail, Integer>{

	public abstract EmployeeDetail getByEmpId(int id);

	public abstract List<EmployeeDetail> findByEmpEmail(String email);

}
