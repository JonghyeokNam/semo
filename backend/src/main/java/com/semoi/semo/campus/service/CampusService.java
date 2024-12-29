package com.semoi.semo.campus.service;

import com.semoi.semo.campus.domain.Campus;
import com.semoi.semo.campus.dto.CampusActScoreResponseDto;
import com.semoi.semo.campus.dto.CampusRecScoreResponseDto;
import com.semoi.semo.campus.enums.CampusName;

import java.util.List;

public interface CampusService {
    Campus getCampusOrElseThrow(CampusName campusName);
    List<CampusRecScoreResponseDto> getCampusRecRankingByYear(int year);
    List<CampusActScoreResponseDto> getCampusActRankingByYear(int year);

    void calculateAndSaveCampusYearlyScores();
}
