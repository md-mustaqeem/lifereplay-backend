package com.life.ai.repository;

import com.life.ai.model.LifeEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LifeEventRepository extends JpaRepository<LifeEvent,Long> {

//    List<LifeEvent> findByUserId(Long userId);
//
//    // OLD (event date based)
//    List<LifeEvent> findTop5ByUserIdOrderByEventDateDesc(Long userId);
//
//    // NEW (createdAt based – recent fix)
//    List<LifeEvent> findTop5ByUserIdOrderByCreatedAtDesc(Long userId);
//
//
//
//    long countByUserId(Long userId);
//    long countByUserIdAndMood(Long userId, String mood);
//
//    // Search by keyword
//    List<LifeEvent> findByUserIdAndTitleContainingIgnoreCase(
//            Long userId, String keyword);
//
//    //  Search by mood
//    List<LifeEvent> findByUserIdAndMood(
//            Long userId, String mood);
//
//    // Search by date range
//    List<LifeEvent> findByUserIdAndEventDateBetween(
//            Long userId, LocalDate start, LocalDate end);
//
//    //  Combined search
//    @Query("""
//       SELECT l FROM LifeEvent l
//       WHERE l.user.id = :userId
//       AND (
//          LOWER(l.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
//          OR LOWER(l.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))
//          OR LOWER(l.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
//       )
//       AND (:mood IS NULL OR :mood = '' OR l.mood = :mood)
//    """)
//    List<LifeEvent> searchMemories(
//            @Param("userId") Long userId,
//            @Param("keyword") String keyword,
//            @Param("mood") String mood
//    );
//
//    // Negative moods (count each memory)
//    @Query("""
//       SELECT l.eventDate
//       FROM LifeEvent l
//       WHERE l.user.id = :userId
//       AND l.mood IN ('Sad','Angry')
//       ORDER BY l.eventDate DESC
//    """)
//    List<LocalDate> findLastNegativeDays(
//            @Param("userId") Long userId,
//            Pageable pageable
//    );
//
//    // Positive moods (count each memory)
//    @Query("""
//       SELECT l.eventDate
//       FROM LifeEvent l
//       WHERE l.user.id = :userId
//       AND l.mood IN ('Happy','Good')
//       ORDER BY l.eventDate DESC
//    """)
//    List<LocalDate> findLastPositiveDays(
//            @Param("userId") Long userId,
//            Pageable pageable
//    );
//
//    List<LifeEvent> findByUser_IdAndEventDateBetween(
//            Long userId, LocalDate start, LocalDate end
//    );
//
//    List<LifeEvent> findByUserIdOrderByEventDateAsc(Long userId);
}
