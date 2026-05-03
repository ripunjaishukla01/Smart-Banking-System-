package com.bank.service;

import com.bank.entity.Transaction;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatementService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<String, Object> getMonthlyStatement(String accountNumber, String month) {
        YearMonth yearMonth = YearMonth.parse(month);
        List<Transaction> allTransactions =
                transactionRepository.findBySenderAccountOrReceiverAccountOrderByTransactionTimeDesc(accountNumber, accountNumber);

        List<Transaction> filtered = allTransactions.stream()
                .filter(tx -> YearMonth.from(tx.getTransactionTime()).equals(yearMonth))
                .collect(Collectors.toList());

        double totalCredit = filtered.stream()
                .filter(tx -> accountNumber.equals(tx.getReceiverAccount()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalDebit = filtered.stream()
                .filter(tx -> accountNumber.equals(tx.getSenderAccount()) && !tx.getSenderAccount().equals(tx.getReceiverAccount()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        Map<String, Object> response = new HashMap<>();
        response.put("accountNumber", accountNumber);
        response.put("month", month);
        response.put("totalTransactions", filtered.size());
        response.put("totalCredit", totalCredit);
        response.put("totalDebit", totalDebit);
        response.put("transactions", filtered);

        return response;
    }   
}