package com.semoi.semo.campus.repository;

import com.semoi.semo.campus.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 차현철
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);
}
