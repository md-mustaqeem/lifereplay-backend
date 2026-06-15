package com.life.ai.controller;

import com.life.ai.model.OtpToken;
import com.life.ai.model.User;
import com.life.ai.repository.OtpRepository;
import com.life.ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final OtpRepository otpRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;

    //  SEND OTP

    @PostMapping("/forgot-password")
    @Transactional
    public String sendOtp(@RequestParam String mobile) {

        // OLD OTP delete (IMPORTANT)
        otpRepo.deleteByMobile(mobile);

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        OtpToken token = new OtpToken();
        token.setMobile(mobile);
        token.setOtp(otp);
        token.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepo.save(token);

        System.out.println("OTP is: " + otp); // 🔥 TEMP (SMS later)

        return "OTP sent";
    }

    // VERIFY OTP

    @PostMapping("/verify-otp")
    @Transactional
    public String verifyOtp(@RequestParam String mobile,
                            @RequestParam String otp) {

        //  LATEST OTP only (FIXED)
        OtpToken token = otpRepo
                .findTopByMobileOrderByExpiryTimeDesc(mobile)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!token.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        //  OTP used → delete
        otpRepo.deleteByMobile(mobile);

        return "OTP verified";
    }

    //  RESET PASSWORD

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String mobile,
                                @RequestParam String newPassword) {

        User user = userRepo.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        return "Password updated";
    }
}
