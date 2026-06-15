package com.life.ai.controller;

import com.life.ai.dto.HeatmapResponse;
import com.life.ai.dto.MoodTrendResponse;
import com.life.ai.model.LifeEvent;
import com.life.ai.model.User;
import com.life.ai.repository.LifeEventRepository;
import com.life.ai.repository.MemoryRepository;
import com.life.ai.service.BurnoutService;
import com.life.ai.service.InsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/insights")
@RequiredArgsConstructor
public class InsightController {

    private final InsightService insightService;
    private final BurnoutService burnoutService;

    @GetMapping("/mood-trend")
    public List<MoodTrendResponse> moodTrend(
            @AuthenticationPrincipal User user) {
        return insightService.moodTrend(user.getId());
    }

    @GetMapping("/heatmap")
    public List<HeatmapResponse> heatmap(
            @AuthenticationPrincipal User user) {
        return insightService.heatmap(user.getId());
    }

    @GetMapping("/mood-matrix")
    public List<Map<String, Object>> moodMatrix(
            @AuthenticationPrincipal User user) {
        return insightService.moodMatrix(user.getId());
    }

}
