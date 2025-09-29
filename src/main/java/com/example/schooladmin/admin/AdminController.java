package com.example.schooladmin.admin;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.activity.ActivityLogService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private  final ActivityLogService activityLogService;

    @PostMapping("/register")
    public ResponseEntity<Admin> register(@RequestBody RegisterAdminDTO dto) {
        activityLogService.log("ADMIN", "Enregistrement d'un nouvel admin : " + dto.getNom());
        return ResponseEntity.ok(adminService.registerAdmin(dto));
    }


    //Get all admins
    @GetMapping("/all")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }
}