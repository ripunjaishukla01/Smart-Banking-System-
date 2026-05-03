package com.bank.service;

import com.bank.entity.User;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Map<String, Object> uploadProfilePhoto(String accountNumber, MultipartFile file) throws IOException {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByAccountNumber(accountNumber).orElse(null);
        if (user == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalName = file.getOriginalFilename();
        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String fileName = UUID.randomUUID() + extension;
        File destination = new File(dir, fileName);
        file.transferTo(destination);

        user.setProfilePhoto("/" + uploadDir + "/" + fileName);
        userRepository.save(user);

        response.put("success", true);
        response.put("message", "Profile photo uploaded successfully");
        response.put("photoUrl", user.getProfilePhoto());
        return response;
    }

    public Map<String, Object> getProfile(String accountNumber) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findByAccountNumber(accountNumber).orElse(null);

        if (user == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        response.put("success", true);
        response.put("fullName", user.getFullName());
        response.put("email", user.getEmail());
        response.put("accountNumber", user.getAccountNumber());
        response.put("balance", user.getBalance());
        response.put("role", user.getRole());
        response.put("profilePhoto", user.getProfilePhoto());
        return response;
    }
}