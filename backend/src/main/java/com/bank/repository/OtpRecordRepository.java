package com.bank.repository;

import com.bank.entity.OtpRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRecordRepository extends JpaRepository<OtpRecord, Long> {
    Optional<OtpRecord> findTopByEmailOrderByIdDesc(String email);
}