package com.bank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fixed_deposits")
public class FixedDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private Double amount;
    private Integer years;
    private Double interestRate;
    private Double maturityAmount;
    private LocalDateTime createdAt;

    public FixedDeposit() {
    }

    public FixedDeposit(String accountNumber, Double amount, Integer years, Double interestRate,
                        Double maturityAmount, LocalDateTime createdAt) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.years = years;
        this.interestRate = interestRate;
        this.maturityAmount = maturityAmount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getYears() {
        return years;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public Double getMaturityAmount() {
        return maturityAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public void setMaturityAmount(Double maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}