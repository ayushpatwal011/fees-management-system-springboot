package com.example.feemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {
    private Long id;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;

}
