package com.semoi.semo.campus.dto;

import com.semoi.semo.campus.domain.CampusYearlyScore;

public record CampusRecScoreResponseDto(
        String campusName,
        int score
) {
    public static CampusRecScoreResponseDto of(CampusYearlyScore campusYearlyScore) {
        return new CampusRecScoreResponseDto(
                campusYearlyScore.getCampus().getCampusName().getName(),
                campusYearlyScore.getRecScore()
        );
    }
}
