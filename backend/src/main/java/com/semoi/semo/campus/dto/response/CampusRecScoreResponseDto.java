package com.semoi.semo.campus.dto.response;

import com.semoi.semo.campus.domain.CampusYearlyScore;

// 차현철
public record CampusRecScoreResponseDto(
        String campusName,
        int score
) {
    public static CampusRecScoreResponseDto of(CampusYearlyScore campusYearlyScore) {
        return new CampusRecScoreResponseDto(
                campusYearlyScore.getCampus().getName(),
                campusYearlyScore.getRecScore()
        );
    }
}
