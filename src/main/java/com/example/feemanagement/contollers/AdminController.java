package com.example.feemanagement.contollers;

import com.example.feemanagement.dto.AdminDto;
import com.example.feemanagement.dto.AdminLoginRequest;
import com.example.feemanagement.dto.AdminUpdateRequest;
import com.example.feemanagement.entity.Admin;
import com.example.feemanagement.payload.ApiResponse;
import com.example.feemanagement.services.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin( origins = "http://localhost:5173")
public class AdminController {

    private final AdminService adminService;
    private final ModelMapper modelMapper;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AdminDto>> login(@Valid @RequestBody AdminLoginRequest req) {
        Admin admin = adminService.login(req);
        AdminDto dto = modelMapper.map(admin, AdminDto.class);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", dto));
    }

    @PutMapping("/update/{adminId}")
    public ResponseEntity<ApiResponse<AdminDto>> update(@PathVariable Long adminId,
                                                     @Valid @RequestBody AdminUpdateRequest req) {
        Admin updated = adminService.updateAdmin(adminId, req);
        AdminDto dto = modelMapper.map(updated, AdminDto.class);
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin updated", dto));
    }

}
