package com.life.ai.repository;

import com.life.ai.model.LifeEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MemoryRepository extends JpaRepository<LifeEvent, Long> {

    //  Combined search
    @Query("""
       SELECT l FROM LifeEvent l
       WHERE l.user.id = :userId
       AND (
          LOWER(l.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
          OR LOWER(l.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))
          OR LOWER(l.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
       )
       AND (:mood IS NULL OR :mood = '' OR l.mood = :mood)
    """)
    List<LifeEvent> searchMemories(
            @Param("userId") Long userId,
            @Param("keyword") String keyword,
            @Param("mood") String mood
    );

    // Top five createdAt based
    List<LifeEvent> findTop5ByUserIdOrderByCreatedAtDesc(Long userId);

    List<LifeEvent> findByUserId(Long userId);

    long countByUserId(Long userId);

    long countByUserIdAndMood(Long userId, String mood);

    List<LifeEvent> findByUserIdOrderByEventDateAsc(Long userId);
}
