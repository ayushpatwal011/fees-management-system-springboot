package com.example.feemanagement.contollers;


import com.example.feemanagement.dto.StudentDto;
import com.example.feemanagement.dto.StudentUpdateDto;
import com.example.feemanagement.entity.Student;
import com.example.feemanagement.payload.ApiResponse;
import com.example.feemanagement.repositories.StudentRepository;
import com.example.feemanagement.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin( origins = "http://localhost:5173")
public class StudentController {
    private final StudentService studentService;

//    create student
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDto>> create(@Valid @RequestBody StudentDto dto) {
        Student created = studentService.create(dto);
        StudentDto resp = studentService.toDto(created); // use service method to include pendingFee
        return ResponseEntity.ok(new ApiResponse<>(true, "Student created", resp));
    }

//    get all the students
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDto>>> list() {
        List<StudentDto> list = studentService.findAll()
                .stream().map(studentService::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Students fetched", list));
    }

//    get student by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        StudentDto resp = studentService.toDto(student);
        return ResponseEntity.ok(new ApiResponse<>(true, "Student fetched successfully", resp));
    }

//    update student
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> update(@PathVariable Long id, @Valid @RequestBody StudentUpdateDto dto) {
        Student updated = studentService.update(id,dto);
        StudentDto resp = studentService.toDto(updated);
        return ResponseEntity.ok(new ApiResponse<>(true, "Student updated successfully", resp));
        }

//     delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Student deleted successfully", null));
    }

//    get student by roll number
@GetMapping("/roll-no/{rollNo}")
public ResponseEntity<ApiResponse<StudentDto>> getStudentByRollNo(@PathVariable String rollNo) {
    Student student = studentService.findByRollNo(rollNo);
    StudentDto resp = studentService.toDto(student);
    return ResponseEntity.ok(new ApiResponse<>(true, "Student fetched successfully", resp));
}


}
