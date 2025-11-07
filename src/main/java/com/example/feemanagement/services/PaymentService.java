package com.example.feemanagement.services;


import com.example.feemanagement.dto.PaymentDto;
import com.example.feemanagement.entity.Payment;
import com.example.feemanagement.entity.Student;
import com.example.feemanagement.repositories.PaymentRepository;
import com.example.feemanagement.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

// Record new payment
    @Transactional
    public PaymentDto create(PaymentDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create new payment entity
        Payment payment = Payment.builder()
                .student(student)
                .amountPaid(dto.getAmountPaid())
                .paymentMode(dto.getPaymentMode())
                .remarks(dto.getRemarks())
                .paymentDate(dto.getPaymentDate() != null ? dto.getPaymentDate() : LocalDate.now())
                .build();

        paymentRepository.save(payment);

        // Update student's paid fee
        student.setPaidFee(student.getPaidFee() + dto.getAmountPaid());
        student.setLastPaymentDate(payment.getPaymentDate());
        studentRepository.save(student);

        return mapper.map(payment, PaymentDto.class);
    }

// Get all payments
    public List<PaymentDto> getAll() {
        return paymentRepository.findAll()
                .stream().map(p -> mapper.map(p, PaymentDto.class))
                .collect(Collectors.toList());
    }

// Get payments by student ID
    public List<PaymentDto> getByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return student.getPayments().stream()
                .map(p -> mapper.map(p, PaymentDto.class))
                .collect(Collectors.toList());
    }
}
