package com.semoi.semo.Campus.repository;

import com.semoi.semo.Campus.domain.Campus;
import com.semoi.semo.Campus.enums.CampusName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Optional<Campus> findByCampusName(CampusName campusName);
}
