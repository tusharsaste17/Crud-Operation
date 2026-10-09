package com.example.crud.service;

import com.example.crud.dto.StudentRequestDto;
import com.example.crud.dto.StudentResponseDto;
import com.example.crud.entity.StudentDetails;
import com.example.crud.exception.StudentNotFoundException;
import com.example.crud.repo.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    // =====================================================
    // GET ALL STUDENTS
    // =====================================================

    public List<StudentResponseDto> getAllStudents() {

        List<StudentDetails> students = studentRepository.findAll();

        return students.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // =====================================================
    // GET STUDENT BY ID
    // =====================================================
    public StudentResponseDto getStudentById(Long studentId) {

        StudentDetails student = studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        "Student not found with ID: "
                                                + studentId
                                )
                        );

        return convertToResponseDTO(student);
    }

    // =====================================================
    // UPDATE STUDENT
    // =====================================================
    public StudentResponseDto updateStudent(Long studentId,StudentRequestDto requestDTO) {

        // Find existing student
        StudentDetails existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with ID: "
                                        + studentId
                        )
                );


        // Update entity using DTO
        existingStudent.setFirstName(requestDTO.getFirstName());
        existingStudent.setLastName(requestDTO.getLastName());
        existingStudent.setEmail(requestDTO.getEmail());
        existingStudent.setPhone(requestDTO.getPhone());
        existingStudent.setDateOfBirth(requestDTO.getDateOfBirth());
        existingStudent.setGender(requestDTO.getGender());
        existingStudent.setCourse(requestDTO.getCourse());
        existingStudent.setAdmissionDate(requestDTO.getAdmissionDate());
        existingStudent.setMarks(requestDTO.getMarks());
        // Save entity
        StudentDetails updatedStudent = studentRepository.save(existingStudent);
        // Convert Entity → Response DTO
        return convertToResponseDTO(updatedStudent);
    }



    // =====================================================
    // ADD NEW STUDENT
    // =====================================================
    public StudentResponseDto addNewStudent(StudentRequestDto requestDTO) {

        // add new student
        StudentDetails addStudent = new StudentDetails();


        // Update entity using DTO
        addStudent.setFirstName(requestDTO.getFirstName());
        addStudent.setLastName(requestDTO.getLastName());
        addStudent.setEmail(requestDTO.getEmail());
        addStudent.setPhone(requestDTO.getPhone());
        addStudent.setDateOfBirth(requestDTO.getDateOfBirth());
        addStudent.setGender(requestDTO.getGender());
        addStudent.setCourse(requestDTO.getCourse());
        addStudent.setAdmissionDate(requestDTO.getAdmissionDate());
        addStudent.setMarks(requestDTO.getMarks());
        // Save entity
        StudentDetails addedNewStudent = studentRepository.save(addStudent);
        // Convert Entity → Response DTO
        return convertToResponseDTO(addedNewStudent);
    }


    // =====================================================
    // ENTITY → RESPONSE DTO
    // =====================================================
    private StudentResponseDto convertToResponseDTO(StudentDetails student) {

        StudentResponseDto responseDTO = new StudentResponseDto();

        responseDTO.setStudentId(student.getStudentId());
        responseDTO.setFirstName(student.getFirstName());
        responseDTO.setLastName(student.getLastName());
        responseDTO.setEmail(student.getEmail());
        responseDTO.setPhone(student.getPhone());
        responseDTO.setDateOfBirth(student.getDateOfBirth());
        responseDTO.setGender(student.getGender());
        responseDTO.setCourse(student.getCourse());
        responseDTO.setAdmissionDate(student.getAdmissionDate());
        responseDTO.setMarks(student.getMarks());
        responseDTO.setCreatedAt(student.getCreatedAt());

        return responseDTO;
    }

}
