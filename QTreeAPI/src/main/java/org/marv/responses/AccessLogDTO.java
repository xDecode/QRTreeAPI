package org.marv.responses;

import java.time.LocalDateTime;

public class AccessLogDTO {
    private LocalDateTime accessTime;
    private Long qrCodeId;
    private Long shortUrlId;

    public AccessLogDTO(LocalDateTime accessTime, Long qrCodeId, Long shortUrlId) {
        this.accessTime = accessTime;
        this.qrCodeId = qrCodeId;
        this.shortUrlId = shortUrlId;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(LocalDateTime accessTime) {
        this.accessTime = accessTime;
    }

    public Long getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Long qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public Long getShortUrlId() {
        return shortUrlId;
    }

    public void setShortUrlId(Long shortUrlId) {
        this.shortUrlId = shortUrlId;
    }


}