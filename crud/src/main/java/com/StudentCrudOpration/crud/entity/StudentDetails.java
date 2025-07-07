package com.StudentCrudOpration.crud.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import org.hibernate.annotations.GeneratorType;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="student_detailstest")
public class StudentDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int id;
	private String name;
	private String city;
	@Override
	public String toString() {
		return "StudentDetails [id=" + id + ", name=" + name + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

}
