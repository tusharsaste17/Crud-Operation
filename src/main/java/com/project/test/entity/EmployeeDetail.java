package com.project.test.entity;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.project.test.validation.ValidateEmailAnnotation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employee_detail")
public class EmployeeDetail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="emp_id")
	private int empId;
	@Column(name="emp_name")
	private String empName;
	@Column(name="emp_email")
	@Email
	@NotBlank(message="email should be required")
	@ValidateEmailAnnotation(message="emial must me unique")
	private String empEmail;
	@Column(name="emp_mobile_number")
	private String empMobileNumber;
	@Column(name="emp_type")
	private String empType;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	public String getEmpMobileNumber() {
		return empMobileNumber;
	}
	public void setEmpMobileNumber(String empMobileNumber) {
		this.empMobileNumber = empMobileNumber;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	
	@Override
	public String toString() {
		return "EmployeeDetail [empId=" + empId + ", empName=" + empName + ", empEmail=" + empEmail
				+ ", empMobileNumber=" + empMobileNumber + ", empType=" + empType + "]";
	}
}
