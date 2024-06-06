package org.marv.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime accessTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "short_url_id")
    private ShortUrl shortUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qr_code_id")
    private QRCode qrCode;

    public AccessLog() {}

    public AccessLog(ShortUrl shortUrl, QRCode qrCode) {
        this.shortUrl = shortUrl;
        this.qrCode = qrCode;
        this.accessTime = LocalDateTime.now();
    }


}