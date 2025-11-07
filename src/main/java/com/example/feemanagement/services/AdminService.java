package com.example.feemanagement.services;

import com.example.feemanagement.dto.AdminLoginRequest;
import com.example.feemanagement.dto.AdminUpdateRequest;
import com.example.feemanagement.entity.Admin;
import com.example.feemanagement.repositories.AdminRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        initDefaultAdmin("admin@college.com", "admin123");
        System.out.println("✅ Default admin check complete.");
    }

    public Admin initDefaultAdmin(String email, String password) {
        return adminRepository.findByEmail(email)
                .orElseGet(() -> {
                    Admin a = Admin.builder()
                            .email(email)
                            .passwordHash(passwordEncoder.encode(password))
                            .build();
                    System.out.println("✅ Created new default admin: " + email);
                    return adminRepository.save(a);
                });
    }

    public Admin login(AdminLoginRequest req) {
        Admin admin = adminRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), admin.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        return admin;
    }

    public Admin updateAdmin(Long adminId, AdminUpdateRequest req) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if (req.getEmail() != null) admin.setEmail(req.getEmail());
        if (req.getPassword() != null) admin.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        return adminRepository.save(admin);
    }


}
