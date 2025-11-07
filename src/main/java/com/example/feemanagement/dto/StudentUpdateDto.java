package com.example.feemanagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentUpdateDto {
    private String fullName;
    private String contactNo;
    private String parentName;
    private String rollNo;      // optional for updates
    private Long courseId;
    private LocalDate admissionDate;
    private LocalDate lastPaymentDate;
    private Double paidFee;
    private Double totalFee;
}
