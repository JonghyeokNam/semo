package com.semoi.semo.campus.repository;

import com.semoi.semo.campus.domain.Campus;
import com.semoi.semo.campus.dto.CampusTotalScoreAndNameDto;
import com.semoi.semo.campus.enums.CampusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Optional<Campus> findByCampusName(CampusName campusName);

    @Query("""
    SELECT new com.semoi.semo.campus.dto.CampusTotalScoreAndNameDto(
        SUM(u.actScore), 
        SUM(u.recScore), 
        c.campusId
    )
    FROM User u
    JOIN u.campus c
    GROUP BY c.campusId
""")
    List<CampusTotalScoreAndNameDto> findTotalScoresAndCampusIdGroupedByCampusName();
}
