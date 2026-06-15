package com.life.ai.service;


import com.life.ai.dto.HeatmapResponse;
import com.life.ai.dto.MoodTrendResponse;
import com.life.ai.model.LifeEvent;
import com.life.ai.repository.MemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InsightService {

    private final MemoryRepository memoryRepository;

    public List<MoodTrendResponse> moodTrend(Long userId) {

        Map<LocalDate, Map<String, Integer>> map = new LinkedHashMap<>();

        for (LifeEvent e : memoryRepository.findByUserIdOrderByEventDateAsc(userId)) {
            map.putIfAbsent(e.getEventDate(), new HashMap<>());
            map.get(e.getEventDate())
                    .merge(e.getMood(), 1, Integer::sum);
        }

        return map.entrySet().stream()
                .map(e -> new MoodTrendResponse(e.getKey(), e.getValue()))
                .toList();
    }

    public List<HeatmapResponse> heatmap(Long userId) {

        Map<LocalDate, Integer> scoreMap = new HashMap<>();

        for (LifeEvent e : memoryRepository.findByUserId(userId)) {
            int score = switch (e.getMood()) {
                case "Happy" -> 3;
                case "Good" -> 2;
                case "Neutral" -> 1;
                case "Sad" -> -1;
                case "Angry" -> -2;
                default -> 0;
            };
            scoreMap.merge(e.getEventDate(), score, Integer::sum);
        }

        return scoreMap.entrySet().stream()
                .map(e -> new HeatmapResponse(e.getKey(), e.getValue()))
                .toList();
    }

    public List<Map<String, Object>> moodMatrix(Long userId) {

        Map<String, Map<String, Integer>> map = new LinkedHashMap<>();

        for (LifeEvent e : memoryRepository.findByUserIdOrderByEventDateAsc(userId)) {

            String date = e.getEventDate().toString();
            String mood = e.getMood();

            map.putIfAbsent(date, new HashMap<>());
            map.get(date).merge(mood, e.getIntensity(), Integer::sum);
        }

        return map.entrySet().stream()
                .flatMap(entry ->
                        entry.getValue().entrySet().stream().map(m -> {
                            Map<String, Object> res = new HashMap<>();
                            res.put("date", entry.getKey());
                            res.put("mood", m.getKey());
                            res.put("intensity", m.getValue());
                            return res;
                        })
                ).toList();
    }
}

