package com.semoi.semo.Campus.service;

import com.semoi.semo.Campus.domain.Campus;
import com.semoi.semo.Campus.dto.CampusActScoreResponseDto;
import com.semoi.semo.Campus.dto.CampusRecScoreResponseDto;
import com.semoi.semo.Campus.enums.CampusName;

import java.util.List;

public interface CampusService {
    Campus getCampusOrElseThrow(CampusName campusName);
    List<CampusRecScoreResponseDto> getCampusRecRankingByYear(int year);
    List<CampusActScoreResponseDto> getCampusActRankingByYear(int year);

    void calculateAndSaveCampusYearlyScores();
}
