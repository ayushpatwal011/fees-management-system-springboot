package com.example.feemanagement.contollers;

import com.example.feemanagement.dto.CourseDto;
import com.example.feemanagement.entity.Course;
import com.example.feemanagement.payload.ApiResponse;
import com.example.feemanagement.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin( origins = "http://localhost:5173")
public class CourseController {
    private final CourseService courseService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<CourseDto>> create(@Valid @RequestBody CourseDto courseDto) {
        Course saved = courseService.create(modelMapper.map(courseDto, Course.class));
        CourseDto responseDto = modelMapper.map(saved, CourseDto.class);
        return ResponseEntity.ok(new ApiResponse<>(true, "Course Created", responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseDto>>> list() {
        List<CourseDto> list = courseService.findAll().stream().map(
                c-> modelMapper.map(c, CourseDto.class)
        ).toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Course List", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> getById(@PathVariable Long id) {
        Course course = courseService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Course Found", modelMapper.map(course, CourseDto.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> update(@PathVariable Long id,@Valid @RequestBody CourseDto courseDto) {
        Course updated = courseService.update(id, modelMapper.map(courseDto, Course.class));
        CourseDto responseDto = modelMapper.map(updated, CourseDto.class);
        return ResponseEntity.ok(new ApiResponse<>(true, "Course Updated", responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Course deleted", null));
    }
}
