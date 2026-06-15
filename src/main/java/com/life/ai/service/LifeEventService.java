package com.life.ai.service;

import com.life.ai.dto.LifeEventRequest;
import com.life.ai.model.LifeEvent;
import com.life.ai.model.User;
import com.life.ai.repository.LifeEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LifeEventService {

    private final LifeEventRepository repo;
    private final RuleBasedAiService ruleBasedAiService;
    private final CloudinaryService cloudinaryService;

    public LifeEvent addMemory(
            LifeEventRequest request,
            MultipartFile image,
            User user
    ) {

        // DTO → ENTITY
        LifeEvent event = new LifeEvent();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setSummary(request.getSummary());
        event.setMood(request.getMood());
        event.setLocation(request.getLocation());
        event.setMemoryType(request.getMemoryType());
        event.setTags(request.getTags());
        event.setIntensity(request.getIntensity());
        event.setEventDate(request.getEventDate());
        event.setCreatedAt(LocalDateTime.now());
        event.setUser(user);

        // Upload image to Cloudinary
        if (image != null && !image.isEmpty()) {
            saveImage(event, image);
        }

        // AI Enrichment
        ruleBasedAiService.enrich(event);

        // Fallback
        if (event.getAiMood() == null || event.getAiMood().isBlank()) {
            event.setAiMood(event.getMood());
        }

        // Save to database
        return repo.save(event);
    }

    /**
     * Upload image to Cloudinary
     */
    private void saveImage(LifeEvent event, MultipartFile image) {

        String imageUrl = cloudinaryService.uploadImage(image);

        event.setImageUrl(imageUrl);
    }
}