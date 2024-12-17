package com.semoi.semo.user.repository;

import com.semoi.semo.user.domain.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Long> {
}
