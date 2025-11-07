package com.example.feemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseDto {
    private Long courseId;
    @NotBlank
    private String courseName;
    private String semester;
    @NotNull
    private Double feeAmount;
}