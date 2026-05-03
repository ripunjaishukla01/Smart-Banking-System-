package com.bank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_records")
public class OtpRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String otp;
    private LocalDateTime expiryTime;
    private boolean verified;

    public OtpRecord() {
    }

    public OtpRecord(String email, String otp, LocalDateTime expiryTime, boolean verified) {
        this.email = email;
        this.otp = otp;
        this.expiryTime = expiryTime;
        this.verified = verified;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}