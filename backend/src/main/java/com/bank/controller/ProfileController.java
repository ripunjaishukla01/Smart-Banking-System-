package com.bank.controller;

import com.bank.entity.User;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    private final String UPLOAD_DIR = "uploads/";

    // ✅ Get Profile
    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getProfile(@PathVariable String accountNumber) {

        User user = userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("fullName", user.getFullName());
        response.put("email", user.getEmail());
        response.put("accountNumber", user.getAccountNumber());
        response.put("balance", user.getBalance());
        response.put("profilePhoto", user.getProfilePhoto());

        return ResponseEntity.ok(response);
    }

    // ✅ Upload Photo
    @PostMapping("/upload/{accountNumber}")
    public ResponseEntity<?> uploadPhoto(
            @PathVariable String accountNumber,
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            User user = userRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Create folder
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Unique filename
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);

            // Save file
            Files.write(path, file.getBytes());

            // Save path in DB
            user.setProfilePhoto("/uploads/" + fileName);
            userRepository.save(user);

            return ResponseEntity.ok("Photo uploaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed");
        }
    }
}