package com.example.feemanagement.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AdminUpdateRequest {
    @Email
    private String email;
    private String password;
}
