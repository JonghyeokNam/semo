package com.semoi.semo.campus.domain;

import com.semoi.semo.campus.enums.CampusName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "campuses")
public class Campus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campus_id", nullable = false, updatable = false)
    private Long campusId;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private CampusName campusName;

    @OneToMany(mappedBy = "campus", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Course> courses;

    @OneToMany(mappedBy = "campus", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CampusYearlyScore> campusYearlyScore;
}
