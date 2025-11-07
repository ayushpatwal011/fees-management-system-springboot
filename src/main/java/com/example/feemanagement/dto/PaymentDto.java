package com.example.feemanagement.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PaymentDto {

    private Long paymentId;

    @NotNull
    private Long studentId;

    @NotNull
    private Double amountPaid;

    private String paymentMode;
    private String remarks;
    private LocalDate paymentDate;
}
