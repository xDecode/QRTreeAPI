package org.marv.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String originalUrl;
    private String shortCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "qr_code_id")
    private QRCode qrcode;

    @OneToMany(mappedBy = "shortUrl", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccessLog> accessLogs = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public QRCode getQrcode() {
        return qrcode;
    }

    public void setQrcode(QRCode qrcode) {
        this.qrcode = qrcode;
    }

    public List<AccessLog> getAccessLogs() {
        return accessLogs;
    }

    public void setAccessLogs(List<AccessLog> accessLogs) {
        this.accessLogs = accessLogs;
    }
}