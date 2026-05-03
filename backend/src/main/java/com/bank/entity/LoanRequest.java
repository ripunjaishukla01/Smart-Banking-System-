package com.bank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_requests")
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String loanType;
    private Double amount;
    private String reason;
    private String status;
    private LocalDateTime createdAt;

    public LoanRequest() {
    }

    public LoanRequest(String accountNumber, String loanType, Double amount, String reason, String status, LocalDateTime createdAt) {
        this.accountNumber = accountNumber;
        this.loanType = loanType;
        this.amount = amount;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getLoanType() {
        return loanType;
    }

    public Double getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}