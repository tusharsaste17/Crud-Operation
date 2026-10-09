package com.example.crud.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StudentRequestDto {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @Email(message = "Please provide a valid email")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @Size(max = 15, message = "Phone cannot exceed 15 characters")
    private String phone;

    private LocalDate dateOfBirth;

    @Size(max = 10, message = "Gender cannot exceed 10 characters")
    private String gender;

    @Size(max = 100, message = "Course cannot exceed 100 characters")
    private String course;

    private LocalDate admissionDate;

    @DecimalMin(
            value = "0.00",
            message = "Marks cannot be less than 0"
    )
    @DecimalMax(
            value = "100.00",
            message = "Marks cannot be greater than 100"
    )
    private BigDecimal marks;

}
