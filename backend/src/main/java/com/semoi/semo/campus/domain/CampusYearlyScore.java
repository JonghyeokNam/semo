package com.semoi.semo.campus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 차현철
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "campus_yearly_scores")
public class CampusYearlyScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campus_yearly_score_id", nullable = false, updatable = false)
    private Long campusYearlyScoreId;

    @Column(nullable = false, updatable = false)
    private int year;

    @Column(name = "rec_score", nullable = false)
    private int recScore = 0;

    @Column(name = "act_score", nullable = false)
    private int actScore = 0;

    @ManyToOne
    @JoinColumn(name = "campus_id", nullable = false)
    private Campus campus;

    private CampusYearlyScore(Campus campus, int year) {
        this.campus = campus;
        this.year = year;
    }

    public void update(int recScore, int actScore) {
        this.recScore = recScore;
        this.actScore = actScore;
    }

    public static CampusYearlyScore create(Campus campus, int year) {
        return new CampusYearlyScore(campus, year);
    }
}
