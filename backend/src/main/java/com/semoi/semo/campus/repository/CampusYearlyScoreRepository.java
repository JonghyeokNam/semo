package com.semoi.semo.campus.repository;

import com.semoi.semo.campus.domain.Campus;
import com.semoi.semo.campus.domain.CampusYearlyScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampusYearlyScoreRepository extends JpaRepository<CampusYearlyScore, Long> {
    List<CampusYearlyScore> findByYearOrderByRecScoreDesc(int year);

    List<CampusYearlyScore> findByYearOrderByActScoreDesc(int year);

    Optional<CampusYearlyScore> findByCampusAndYear(Campus campus, int currentYear);
}
