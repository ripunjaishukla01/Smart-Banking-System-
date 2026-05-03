package com.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "beneficiaries")
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userAccountNumber;
    private String beneficiaryName;
    private String beneficiaryAccountNumber;
    private String bankName;
    private String ifscCode;

    public Beneficiary() {
    }

    public Beneficiary(String userAccountNumber, String beneficiaryName,
                       String beneficiaryAccountNumber, String bankName, String ifscCode) {
        this.userAccountNumber = userAccountNumber;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
        this.bankName = bankName;
        this.ifscCode = ifscCode;
    }

    public Long getId() {
        return id;
    }

    public String getUserAccountNumber() {
        return userAccountNumber;
    }

    public void setUserAccountNumber(String userAccountNumber) {
        this.userAccountNumber = userAccountNumber;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}