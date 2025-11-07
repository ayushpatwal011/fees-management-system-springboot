package com.example.feemanagement.contollers;


import com.example.feemanagement.dto.PaymentDto;
import com.example.feemanagement.payload.ApiResponse;
import com.example.feemanagement.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

//    create / Student pay fees
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentDto>> createPayment(@Valid @RequestBody PaymentDto dto) {
        PaymentDto savedPayment = paymentService.create(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment recorded successfully", savedPayment));
    }


//  get all payments
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getAllPayments() {
        List<PaymentDto> list = paymentService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "All payments fetched successfully", list));
    }

//    get payment by student id
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getPaymentsByStudent(@PathVariable Long studentId) {
        List<PaymentDto> list = paymentService.getByStudent(studentId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Payments fetched for student", list));
    }

}
