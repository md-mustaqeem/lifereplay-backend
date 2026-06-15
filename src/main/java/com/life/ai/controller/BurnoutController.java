package com.life.ai.controller;


import com.life.ai.model.User;
import com.life.ai.service.BurnoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/burnout")
@RequiredArgsConstructor
public class BurnoutController {

    private final BurnoutService burnoutService;

    @GetMapping("/mood-status")
    public Map<String, String> moodStatus(@AuthenticationPrincipal User user) {

        String status = burnoutService.moodStatus(user.getId());

        Map<String, String> res = new HashMap<>();
        res.put("status", status);
        res.put("message", switch (status) {
            case "BURNOUT" -> "⚠ You seem overwhelmed — take a moment for yourself.";
            case "RECOVERY" -> "🌿 You're doing great. Keep going!";
            case "HAPPY"   -> "😄 You're feeling happy today — keep shining!";
            default       -> "🙂 Your mood is balanced — you're doing well.";
        });
        return res;
    }
}
