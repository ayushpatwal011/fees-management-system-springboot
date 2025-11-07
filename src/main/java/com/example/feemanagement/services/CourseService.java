package com.example.feemanagement.services;


import com.example.feemanagement.entity.Course;
import com.example.feemanagement.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

//    create course
public Course create(Course course) {
    return courseRepository.save(course);
}

//get courses
public List<Course> findAll() {
    return courseRepository.findAll();
}

//get course by id
public Course findById(Long id) {
    return courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
}

//update course
public Course update(Long id, Course updatedCourse) {
    Course existing = findById(id);
    existing.setCourseName(updatedCourse.getCourseName());
    existing.setSemester(updatedCourse.getSemester());
    existing.setFeeAmount(updatedCourse.getFeeAmount());
    return courseRepository.save(existing);
}

//delete courses
public void delete(Long id) {
    Course existing = findById(id);
    courseRepository.delete(existing);
}



}
