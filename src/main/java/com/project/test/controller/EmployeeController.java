package com.project.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.test.entity.EmployeeDetail;
import com.project.test.repo.EmployeeRepo;
import com.project.test.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping("/Welcome")
	public String welcome() {
		return "Welcome in Programming world..!!";
	}
	@GetMapping("/allEmployee")
	public List<EmployeeDetail> getAllEmployee() {
		return employeeRepo.findAll();
	}
	
	@GetMapping("/getEmployeeById/{id}")
	public EmployeeDetail getEmployee(@PathVariable("id") int id) {
		return employeeService.getByEmpId(id);
	}
	@PostMapping("/saveEmpDetail")
	public int addEmployee(@Valid @RequestBody EmployeeDetail emp) {
		int empId = employeeService.addEmployeeDetail(emp);
		return empId;
	}
}
