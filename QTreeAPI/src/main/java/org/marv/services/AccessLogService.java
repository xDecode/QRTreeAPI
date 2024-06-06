package org.marv.services;

import org.marv.repository.AccessLogRepository;
import org.marv.responses.AccessLogDTO;
import org.marv.responses.HourlyUsageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessLogService {
    @Autowired
    private AccessLogRepository accessLogRepository;

    public List<HourlyUsageDTO> getUsageByHour() {
        List<Object[]> results = accessLogRepository.countAccessesByHour();
        return results.stream()
                .map(result -> {
                    int hour = ((Number) result[1]).intValue();
                    long count = ((Number) result[0]).longValue();
                    long qrCodeId = result[2] != null ? ((Number) result[2]).longValue() : 0;
                    long shortUrlId = result[3] != null ? ((Number) result[3]).longValue() : 0;
                    return new HourlyUsageDTO(hour, count, qrCodeId, shortUrlId);
                })
                .collect(Collectors.toList());
    }
    public List<HourlyUsageDTO> getUsageByHourId(long id) {
        List<Object[]> results = accessLogRepository.countAccessesByHourId(id);
        return results.stream()
                .map(result -> {
                    int hour = ((Number) result[1]).intValue();
                    long count = ((Number) result[0]).longValue();
                    long qrCodeId = result[2] != null ? ((Number) result[2]).longValue() : 0;
                    long shortUrlId = result[3] != null ? ((Number) result[3]).longValue() : 0;
                    return new HourlyUsageDTO(hour, count, qrCodeId, shortUrlId);
                })
                .collect(Collectors.toList());
    }
    public List<AccessLogDTO> getUsage() {
        List<Object[]> results = accessLogRepository.retrieveEveryAccess();
        return results.stream()
                .map(result -> {
                    LocalDateTime accessTime = result[0] != null ? ((Timestamp) result[0]).toLocalDateTime() : null;
                    long qrCodeId = result[1] != null ? ((Number) result[1]).longValue() : 0;
                    long shortUrlId = result[2] != null ? ((Number) result[2]).longValue() : 0;
                    return new AccessLogDTO(accessTime, qrCodeId, shortUrlId);
                })
                .collect(Collectors.toList());
    }
    public List<AccessLogDTO> getUsageById(Long shortUrlId) {
        List<Object[]> results = accessLogRepository.retrieveEveryAccessById(shortUrlId);
        return results.stream()
                .map(result -> {
                    LocalDateTime accessTime = result[0] != null ? ((Timestamp) result[0]).toLocalDateTime() : null;
                    long qrCodeId = result[1] != null ? ((Number) result[1]).longValue() : 0;
                    long shortUrlIdPr = result[2] != null ? ((Number) result[2]).longValue() : 0;
                    return new AccessLogDTO(accessTime, qrCodeId, shortUrlIdPr);
                })
                .collect(Collectors.toList());
    }


}