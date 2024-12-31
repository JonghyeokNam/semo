package com.semoi.semo.campus.dto.response;

import com.semoi.semo.campus.domain.CampusYearlyScore;

public record CampusActScoreResponseDto(
        String campusName,
        int score
) {
    public static CampusActScoreResponseDto of(CampusYearlyScore campusYearlyScore) {
        return new CampusActScoreResponseDto(
                campusYearlyScore.getCampus().getName(),
                campusYearlyScore.getActScore()
        );
    }
}
