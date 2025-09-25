package com.example.schooladmin.activity;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/activities")
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogService activityLogService;
    private final ActivityLogRepository activityLogRepository;

    @GetMapping("/recent")
    public List<ActivityLog> recent() {
        return activityLogRepository.findTop10ByOrderByCreatedAtDesc();
    }
} 