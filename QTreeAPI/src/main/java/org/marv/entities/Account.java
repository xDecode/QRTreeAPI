package org.marv.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Account {
    @Id
    private String id;
    private boolean isActive;
    private String username;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<QRCode> qrCodes; Long[] qrs;

    protected Account() {
        this.username = username;
        this.qrCodes = new ArrayList<>();
    }

    public Account(String username, String id) {
        this.username = username;
        this.id = id;
        this.qrCodes = new ArrayList<>();
        this.isActive = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<QRCode> getQrCodes() {
        return qrCodes;
    }

    public void setQrCodes(List<QRCode> qrCodes) {
        this.qrCodes = qrCodes;
    }

    public void addQRCode(QRCode qrCode) {
        qrCodes.add(qrCode);
        qrCode.setAccount(this);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}