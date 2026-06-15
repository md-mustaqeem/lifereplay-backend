package com.life.ai.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class LifeEventRequest {
    private String title;
    private String description;
    private String summary;
    private String memoryType;
    private String mood;
    private int intensity;
    private LocalDate eventDate;
    private String location;
    private String tags;
    private String imageUrl;
}
