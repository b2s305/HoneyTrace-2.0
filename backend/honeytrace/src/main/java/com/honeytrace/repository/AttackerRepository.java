package com.honeytrace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.honeytrace.entity.Attacker;

public interface AttackerRepository extends JpaRepository<Attacker, Long> {

    // ==========================================
    // Recent Attackers
    // ==========================================

    List<Attacker> findTop10ByOrderByIdDesc();

    List<Attacker> findTop10ByOrderByLastSeenDesc();

    // ==========================================
    // World Map
    // ==========================================

    List<Attacker> findAllByLatitudeIsNotNullAndLongitudeIsNotNull();

    // ==========================================
    // Search
    // ==========================================

    List<Attacker> findByIpAddressContainingIgnoreCase(String ipAddress);

    // ==========================================
    // Risk Statistics
    // ==========================================

    long countByRiskScoreLessThan(Integer riskScore);

    long countByRiskScoreBetween(Integer min, Integer max);

    long countByRiskScoreGreaterThanEqual(Integer riskScore);

    // ==========================================
    // Country Statistics
    // ==========================================

    @Query("""
        SELECT a.country
        FROM Attacker a
        WHERE a.country IS NOT NULL
        GROUP BY a.country
        ORDER BY COUNT(a) DESC
    """)
    List<String> getTopCountries();

    // ==========================================
    // Latest High Risk Attackers
    // ==========================================

    List<Attacker> findTop5ByRiskScoreGreaterThanEqualOrderByLastSeenDesc(Integer riskScore);

}