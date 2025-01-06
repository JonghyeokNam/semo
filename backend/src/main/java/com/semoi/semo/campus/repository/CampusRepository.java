package com.semoi.semo.campus.repository;

import com.semoi.semo.campus.domain.Campus;
import com.semoi.semo.campus.dto.CampusTotalScoreAndNameDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// 차현철
public interface CampusRepository extends JpaRepository<Campus, Long> {
    Optional<Campus> findByName(String name);
    Optional<Campus> findByCampusId(Long campusId);

    @Query("""
    SELECT new com.semoi.semo.campus.dto.CampusTotalScoreAndNameDto(
        SUM(u.actScore),
        SUM(u.recScore),
        cam.campusId
    )
    FROM User u
    JOIN u.course cos
    JOIN cos.campus cam
    GROUP BY cam.campusId
""")
    List<CampusTotalScoreAndNameDto> findTotalScoresAndCampusIdGroupedByCampusName();

}
