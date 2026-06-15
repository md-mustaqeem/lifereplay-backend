package com.life.ai.service;

import com.life.ai.model.LifeEvent;
import com.life.ai.repository.LifeEventRepository;
import com.life.ai.repository.MemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private  final MemoryRepository memoryRepository;

    // search by title, nood, location, date etc
    public List<LifeEvent> searchMemories(Long userId, String q, String mood) {
        if (q == null) q = "";
        return memoryRepository.searchMemories(userId, q, mood);
    }

    //all memory views
    public List<LifeEvent> getAllMemories(Long userId) {
        return memoryRepository.findByUserId(userId);
    }

    //recent 5 view user
    public List<LifeEvent> getRecentMemories(Long userId) {
        return memoryRepository.findTop5ByUserIdOrderByCreatedAtDesc(userId);
    }


    public Map<String, Long> getStats(Long userId) {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", memoryRepository.countByUserId(userId));
        stats.put("happy", memoryRepository.countByUserIdAndMood(userId, "Happy"));
        stats.put("stress", memoryRepository.countByUserIdAndMood(userId, "Sad"));
        return stats;
    }


    public List<LifeEvent> getTimeline(Long userId) {
        return memoryRepository.findByUserIdOrderByEventDateAsc(userId);
    }

}
