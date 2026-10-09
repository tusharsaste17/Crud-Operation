package com.example.crud.controller;

import com.example.crud.dto.StudentRequestDto;
import com.example.crud.dto.StudentResponseDto;
import com.example.crud.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // =====================================================
    // GET ALL STUDENTS
    // =====================================================
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        List<StudentResponseDto> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // =====================================================
    // GET STUDENT BY ID
    // =====================================================
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long studentId) {
        StudentResponseDto student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(student);
    }

    // =====================================================
    // UPDATE STUDENT
    // =====================================================
    @PostMapping("/addNew")
    public ResponseEntity<StudentResponseDto> addNewStudent(@Valid @RequestBody StudentRequestDto requestDTO) {
        StudentResponseDto updatedStudent = studentService.addNewStudent(requestDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    // =====================================================
    // UPDATE STUDENT
    // =====================================================
    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentRequestDto requestDTO) {
        StudentResponseDto updatedStudent = studentService.updateStudent(studentId,requestDTO);
        return ResponseEntity.ok(updatedStudent);
    }
}
