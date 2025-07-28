package com.project.test.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.project.test.validation.ValidateEmailAnnotation;

public class EmployeeDTO {
	
	private int id;
	private String empName;
	@Email
	@NotBlank(message="email should be required")
	@ValidateEmailAnnotation(message="emial must me unique")
	private String empEmail;

}
