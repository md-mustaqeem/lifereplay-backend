package com.life.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
public class MoodTrendResponse {
    private LocalDate date;
    private Map<String, Integer> moods;
}
