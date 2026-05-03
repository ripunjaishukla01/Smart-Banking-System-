package com.bank.service;

import com.bank.dto.FixedDepositRequest;
import com.bank.entity.FixedDeposit;
import com.bank.repository.FixedDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FixedDepositService {

    @Autowired
    private FixedDepositRepository fixedDepositRepository;

    public FixedDeposit createFd(FixedDepositRequest request) {
        double rate = 7.0;
        double maturityAmount = request.getAmount() + (request.getAmount() * rate * request.getYears() / 100.0);

        FixedDeposit fd = new FixedDeposit(
                request.getAccountNumber(),
                request.getAmount(),
                request.getYears(),
                rate,
                maturityAmount,
                LocalDateTime.now()
        );

        return fixedDepositRepository.save(fd);
    }

    public List<FixedDeposit> getFds(String accountNumber) {
        return fixedDepositRepository.findByAccountNumber(accountNumber);
    }
}