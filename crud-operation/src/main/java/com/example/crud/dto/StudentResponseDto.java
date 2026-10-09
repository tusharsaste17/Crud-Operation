package com.example.crud.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentResponseDto {

    private Long studentId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate dateOfBirth;

    private String gender;

    private String course;

    private LocalDate admissionDate;

    private BigDecimal marks;

    private LocalDateTime createdAt;

}
