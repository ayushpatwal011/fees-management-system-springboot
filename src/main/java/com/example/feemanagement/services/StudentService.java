package com.example.feemanagement.services;

import com.example.feemanagement.dto.StudentDto;
import com.example.feemanagement.dto.StudentUpdateDto;
import com.example.feemanagement.entity.Course;
import com.example.feemanagement.entity.Student;
import com.example.feemanagement.repositories.CourseRepository;
import com.example.feemanagement.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private  final ModelMapper mapper;



// Create new student
    @Transactional
    public Student create(StudentDto dto) {
        //  Find course
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Map DTO → Entity
        Student student = mapper.map(dto, Student.class);
        student.setCourse(course);
        student.setTotalFee(course.getFeeAmount());
        student.setPaidFee(0.0);
        student.setAdmissionDate(dto.getAdmissionDate() != null ? dto.getAdmissionDate() : LocalDate.now());

        // Save student
        return studentRepository.save(student);
    }

// Get all students
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

//  Find student by ID
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

// Update student details
@Transactional
public Student update(Long id, StudentUpdateDto dto) {
    Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));

    if (dto.getFullName() != null) student.setFullName(dto.getFullName());
    if (dto.getContactNo() != null) student.setContactNo(dto.getContactNo());
    if (dto.getParentName() != null) student.setParentName(dto.getParentName());
    if (dto.getLastPaymentDate() != null) student.setLastPaymentDate(dto.getLastPaymentDate());
    if (dto.getPaidFee() != null) student.setPaidFee(dto.getPaidFee());
    if (dto.getTotalFee() != null) student.setTotalFee(dto.getTotalFee());
    if (dto.getAdmissionDate() != null) student.setAdmissionDate(dto.getAdmissionDate());

    // IMPORTANT: only update rollNo if provided (avoid setting null)
    if (dto.getRollNo() != null) student.setRollNo(dto.getRollNo());

    if (dto.getCourseId() != null) {
        Course c = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        student.setCourse(c);
    }

    student.setUpdatedAt(Instant.now());
    return studentRepository.save(student);
}


// Delete student
    public void delete(Long id) {
        Student existing = findById(id);
        studentRepository.delete(existing);
    }

//  find by roll number
    public Student findByRollNo(String rollNo) {
        return  studentRepository.findByRollNo(rollNo).orElseThrow(() -> new RuntimeException("Student not found with rollNo: " + rollNo));
    }


// Convert Student → StudentDto with pendingFee
    public StudentDto toDto(Student s) {
        StudentDto dto = mapper.map(s, StudentDto.class);
        dto.setCourseId(s.getCourse() != null ? s.getCourse().getCourseId() : null);
        dto.setPendingFee(s.getTotalFee() - s.getPaidFee());
        return dto;
    }
}
