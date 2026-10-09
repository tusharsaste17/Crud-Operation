package com.example.crud.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table( name = "student_details")
@Data
public class StudentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "course")
    private String course;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Column(name = "marks")
    private BigDecimal marks;

    @Column(
            name = "created_at",
            insertable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

}
