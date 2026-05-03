package com.bank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Double amount;
    private String senderAccount;
    private String receiverAccount;
    private LocalDateTime transactionTime;

    public Transaction() {
    }

    public Transaction(String type, Double amount, String senderAccount, String receiverAccount, LocalDateTime transactionTime) {
        this.type = type;
        this.amount = amount;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.transactionTime = transactionTime;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public Double getAmount() { return amount; }
    public String getSenderAccount() { return senderAccount; }
    public String getReceiverAccount() { return receiverAccount; }
    public LocalDateTime getTransactionTime() { return transactionTime; }

    public void setId(Long id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setSenderAccount(String senderAccount) { this.senderAccount = senderAccount; }
    public void setReceiverAccount(String receiverAccount) { this.receiverAccount = receiverAccount; }
    public void setTransactionTime(LocalDateTime transactionTime) { this.transactionTime = transactionTime; }
}