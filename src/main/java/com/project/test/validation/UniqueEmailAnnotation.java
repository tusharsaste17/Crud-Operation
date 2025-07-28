package com.project.test.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.test.repo.EmployeeRepo;

public class UniqueEmailAnnotation implements ConstraintValidator<ValidateEmailAnnotation, String>{

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(employeeRepo.findByEmpEmail(value).size()==0)
		  return true;
		return false;
	}

}
