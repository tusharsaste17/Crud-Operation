package com.project.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.test.entity.EmployeeDetail;
import com.project.test.repo.EmployeeRepo;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	public EmployeeDetail getByEmpId(int id) {
		EmployeeDetail empDetail = null;
		try {
			empDetail = employeeRepo.getByEmpId(id);
			System.out.println("emp details "+empDetail);
			return empDetail;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public int addEmployeeDetail(EmployeeDetail emp) {
		int empId = 0;
		try {
			EmployeeDetail empDetail = employeeRepo.save(emp);
			empId = empDetail.getEmpId();
			System.out.println("empId="+empId);
			return empId;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return empId;
	}

}
