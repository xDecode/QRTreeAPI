package org.marv.controller;

import org.marv.responses.AccessLogDTO;
import org.marv.responses.HourlyUsageDTO;
import org.marv.services.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalyticsController {

    @Autowired
    private AccessLogService accessLogService;

    @GetMapping("/api/analytics/hourly")
    public ResponseEntity<Object> getUsageByHour() {
        try {
            List<HourlyUsageDTO> usageStats = accessLogService.getUsageByHour();
            return ResponseEntity.ok(usageStats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/api/analytics/hourly/{id}")
    public ResponseEntity<Object> getUsageByHour(@PathVariable("id") Long shortUrlId) {
        try {
            List<HourlyUsageDTO> usageStats = accessLogService.getUsageByHourId(shortUrlId);
            return ResponseEntity.ok(usageStats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/api/analytics/fullaccess")
    public ResponseEntity<Object> getUsage() {
        try {
            List<AccessLogDTO> usageStats = accessLogService.getUsage();
            return ResponseEntity.ok(usageStats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/api/analytics/fullaccess/{id}")
    public ResponseEntity<Object> getUsageById(@PathVariable("id") Long shortUrlId) {
        try {
            List<AccessLogDTO> usageStats = accessLogService.getUsageById(shortUrlId);
            return ResponseEntity.ok(usageStats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}