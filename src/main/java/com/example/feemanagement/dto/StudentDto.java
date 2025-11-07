package com.example.feemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class StudentDto {
    private Long studentId;

    @NotBlank
    private String fullName;

    @NotBlank
    private String rollNo;

    private String contactNo;
    private String parentName;
    private LocalDate admissionDate;
    private Double totalFee;
    private Double paidFee;
    private LocalDate lastPaymentDate;
    private Long courseId;
    private Double pendingFee;

}
