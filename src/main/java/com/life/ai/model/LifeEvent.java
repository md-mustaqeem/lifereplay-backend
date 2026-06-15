package com.life.ai.model;

import com.life.ai.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "life_event")
public class LifeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 🔥 MUST
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "summary")
    private String summary;

    @Column(name = "mood")
    private String mood;

    @Column(name = "location")
    private String location;

    @Column(name = "memory_type")
    private String memoryType;

    @Column(name = "tags")
    private String tags;

    @Column(name = "intensity", nullable = false)
    private Integer intensity;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "ai_summary")
    private String aiSummary;

    @Column(name = "ai_mood")
    private String aiMood;

    @Column(name = "importance_score")
    private Double importanceScore;

}
