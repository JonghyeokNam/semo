package com.semoi.semo.Campus.dto;

import com.semoi.semo.Campus.domain.CampusYearlyScore;

public record CampusActScoreResponseDto(
        String campusName,
        int score
) {
    public static CampusActScoreResponseDto of(CampusYearlyScore campusYearlyScore) {
        return new CampusActScoreResponseDto(
                campusYearlyScore.getCampus().getCampusName().getName(),
                campusYearlyScore.getActScore()
        );
    }
}
