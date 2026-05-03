package com.bank.service;

import com.bank.dto.LoanRequestDto;
import com.bank.entity.LoanRequest;
import com.bank.repository.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    public LoanRequest createLoanRequest(LoanRequestDto dto) {
        LoanRequest loanRequest = new LoanRequest(
                dto.getAccountNumber(),
                dto.getLoanType(),
                dto.getAmount(),
                dto.getReason(),
                "PENDING",
                LocalDateTime.now()
        );
        return loanRequestRepository.save(loanRequest);
    }

    public List<LoanRequest> getLoansByAccount(String accountNumber) {
        return loanRequestRepository.findByAccountNumber(accountNumber);
    }
}