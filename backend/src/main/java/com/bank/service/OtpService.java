package com.bank.service;

import com.bank.entity.OtpRecord;
import com.bank.repository.OtpRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRecordRepository otpRecordRepository;

    public Map<String, Object> sendOtp(String email) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        OtpRecord record = new OtpRecord(email, otp, LocalDateTime.now().plusMinutes(5), false);
        otpRecordRepository.save(record);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "OTP generated successfully");
        response.put("email", email);
        response.put("otp", otp); // simulation only
        return response;
    }

    public Map<String, Object> verifyOtp(String email, String otp) {
        Map<String, Object> response = new HashMap<>();

        OtpRecord record = otpRecordRepository.findTopByEmailOrderByIdDesc(email).orElse(null);

        if (record == null) {
            response.put("success", false);
            response.put("message", "OTP not found");
            return response;
        }

        if (record.isVerified()) {
            response.put("success", false);
            response.put("message", "OTP already used");
            return response;
        }

        if (record.getExpiryTime().isBefore(LocalDateTime.now())) {
            response.put("success", false);
            response.put("message", "OTP expired");
            return response;
        }

        if (!record.getOtp().equals(otp)) {
            response.put("success", false);
            response.put("message", "Invalid OTP");
            return response;
        }

        record.setVerified(true);
        otpRecordRepository.save(record);

        response.put("success", true);
        response.put("message", "OTP verified successfully");
        return response;
    }
}