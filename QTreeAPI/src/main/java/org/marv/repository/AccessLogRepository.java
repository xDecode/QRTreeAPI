package org.marv.repository;

import org.marv.entities.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    @Query(value = "SELECT COUNT(*), EXTRACT(HOUR FROM access_time), qr_code_id, short_url_id FROM access_log GROUP BY EXTRACT(HOUR FROM access_time), qr_code_id, short_url_id", nativeQuery = true)
    List<Object[]> countAccessesByHour();
    @Query(value = "SELECT access_time, qr_code_id, short_url_id FROM access_log", nativeQuery = true)
    List<Object[]> retrieveEveryAccess();
    @Query(value = "SELECT access_time, qr_code_id, short_url_id FROM access_log WHERE short_url_id = ?1", nativeQuery = true)
    List<Object[]> retrieveEveryAccessById(Long shortUrlId);
    @Query(value = "SELECT COUNT(*), EXTRACT(HOUR FROM access_time), qr_code_id, short_url_id FROM access_log WHERE short_url_id = ?1 GROUP BY EXTRACT(HOUR FROM access_time), qr_code_id, short_url_id", nativeQuery = true)
    List<Object[]> countAccessesByHourId(long shortUrlId);

}