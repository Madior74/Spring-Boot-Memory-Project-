package com.example.schooladmin.admin;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Admin> register(@RequestBody RegisterAdminDTO dto) {
        return ResponseEntity.ok(adminService.registerAdmin(dto));
    }


    //Get all admins
    @GetMapping("/all")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }
}