package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface D_FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("""
        SELECT f 
        FROM Feedback f
            JOIN f.user u
            JOIN f.flight fl
        WHERE
            (:username IS NULL OR u.userName LIKE %:username%)
        AND (:flightCode IS NULL OR fl.flightNumber LIKE %:flightCode%)
        AND (:rating IS NULL OR f.rating = :rating)
        ORDER BY f.feedbackDate DESC
    """)
    List<Feedback> searchFeedback(
            @Param("username") String username,
            @Param("flightCode") String flightCode,
            @Param("rating") Integer rating
    );
}
