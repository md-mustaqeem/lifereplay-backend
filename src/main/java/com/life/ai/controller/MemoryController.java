package com.life.ai.controller;


import com.life.ai.model.LifeEvent;
import com.life.ai.model.User;
import com.life.ai.repository.LifeEventRepository;
import com.life.ai.repository.MemoryRepository;
import com.life.ai.service.LifeEventService;
import com.life.ai.service.MemoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/memory")
@RequiredArgsConstructor
public class MemoryController {

    private final MemoryService memoryService;

    @GetMapping("/search")
    public List<LifeEvent> searchMemories(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String mood,
            @AuthenticationPrincipal(expression = "id") Long userId
    ) {
        return memoryService.searchMemories(userId, q, mood);
    }

    @GetMapping("/my")
    public List<LifeEvent> getMyMemories(
            @AuthenticationPrincipal User user
    ) {
        return memoryService.getAllMemories(user.getId());
    }

    @GetMapping("/recent")
    public List<LifeEvent> getRecentMemories(
            @AuthenticationPrincipal User user
    ) {
        return memoryService.getRecentMemories(user.getId());
    }



    @GetMapping("/stats")
    public Map<String, Long> getStats(
            @AuthenticationPrincipal User user
    ) {
        return memoryService.getStats(user.getId());
    }


    @GetMapping("/timeline")
    public List<LifeEvent> getTimeline(
            @AuthenticationPrincipal User user
    ) {
        return memoryService.getTimeline(user.getId());
    }

}

