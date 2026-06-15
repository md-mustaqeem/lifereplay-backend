package com.life.ai.service;

import com.life.ai.dto.LoginRequest;
import com.life.ai.dto.SignupRequest;
import com.life.ai.model.User;
import com.life.ai.repository.UserRepository;
import com.life.ai.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    //signup ke liye hai
    public String signup(SignupRequest req) {

        if (userRepository.existsByMobile(req.getMobile())) {
            return "Mobile already registered";
        }

        User user = new User();
        user.setName(req.getName());
        user.setMobile(req.getMobile());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        userRepository.save(user);
        return "Signup success";
    }

    //login ke liye hai
    public String login(LoginRequest req) {

        User user = userRepository.findByMobile(req.getMobile())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return jwtService.generateToken(user.getMobile()); // JWT
    }
}
