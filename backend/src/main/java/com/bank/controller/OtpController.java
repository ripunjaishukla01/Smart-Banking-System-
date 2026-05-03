package com.bank.controller;

import com.bank.dto.OtpSendRequest;
import com.bank.dto.OtpVerifyRequest;
import com.bank.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "*")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public Map<String, Object> sendOtp(@RequestBody OtpSendRequest request) {
        return otpService.sendOtp(request.getEmail());
    }

    @PostMapping("/verify")
    public Map<String, Object> verifyOtp(@RequestBody OtpVerifyRequest request) {
        return otpService.verifyOtp(request.getEmail(), request.getOtp());
    }
}