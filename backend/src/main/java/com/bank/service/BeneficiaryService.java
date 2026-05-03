package com.bank.service;

import com.bank.dto.BeneficiaryRequest;
import com.bank.entity.Beneficiary;
import com.bank.repository.BeneficiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    public Beneficiary addBeneficiary(BeneficiaryRequest request) {
        Beneficiary beneficiary = new Beneficiary(
                request.getUserAccountNumber(),
                request.getBeneficiaryName(),
                request.getBeneficiaryAccountNumber(),
                request.getBankName(),
                request.getIfscCode()
        );
        return beneficiaryRepository.save(beneficiary);
    }

    public List<Beneficiary> getBeneficiaries(String userAccountNumber) {
        return beneficiaryRepository.findByUserAccountNumber(userAccountNumber);
    }
}