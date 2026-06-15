package com.life.ai.service;

import com.life.ai.model.LifeEvent;
import org.springframework.stereotype.Service;

@Service
public class RuleBasedAiService {

    public void enrich(LifeEvent event) {

        // =====  SUMMARY =====
        if (event.getDescription() != null && !event.getDescription().isBlank()) {
            String[] parts = event.getDescription().split("\\.");
            event.setAiSummary(parts[0].trim());
        } else {
            event.setAiSummary(event.getTitle());
        }

        // ===== MOOD DETECTION =====
        String text = (
                (event.getTitle() == null ? "" : event.getTitle()) + " " +
                        (event.getDescription() == null ? "" : event.getDescription())
        ).toLowerCase();

        if (text.contains("happy") || text.contains("enjoy") || text.contains("trip")) {
            event.setAiMood("Happy");
        } else if (text.contains("sad") || text.contains("fail") || text.contains("lost")) {
            event.setAiMood("Sad");
        } else if (text.contains("angry") || text.contains("fight")) {
            event.setAiMood("Angry");
        } else {
            event.setAiMood("Neutral");
        }

        // =====  IMPORTANCE SCORE =====
        if (event.getIntensity() != null && event.getIntensity() >= 8) {
            event.setImportanceScore(0.9);
        } else {
            event.setImportanceScore(0.6);
        }
    }
}
