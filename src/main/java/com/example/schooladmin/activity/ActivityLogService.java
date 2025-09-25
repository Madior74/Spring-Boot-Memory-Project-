package com.example.schooladmin.activity;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLog log(String type, String description) {
        ActivityLog log = new ActivityLog();
        log.setType(type);
        log.setDescription(description);
        return activityLogRepository.save(log);
    }
} 