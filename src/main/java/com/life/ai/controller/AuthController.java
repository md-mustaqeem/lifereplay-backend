package com.life.ai.controller;

import com.life.ai.dto.LoginRequest;
import com.life.ai.dto.SignupRequest;
import com.life.ai.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest req) {

        return authService.signup(req);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {

        return authService.login(req);
    }
}
