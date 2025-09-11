package com.project.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.test.entity.StudentDetails;
import com.project.test.repo.StudentRepo;

@RestController
public class StudentController {
	
	@Autowired
	private StudentRepo studentRepo;
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}
	
	
	@SuppressWarnings("deprecation")
	@GetMapping("/student/id")
	public StudentDetails getStudentDetails(@PathVariable("id") int id) {

		return studentRepo.getById(id);
	}
	
	public Integer saveStudentDetails(@RequestBody StudentDetails studentDetails) {
		StudentDetails studDetails = studentRepo.saveAndFlush(studentDetails);
		
		return studDetails.getStudId();
	}

}
