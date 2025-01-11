package com.semoi.semo.campus.service;

import com.semoi.semo.campus.domain.Campus;
import com.semoi.semo.campus.dto.response.CampusActScoreResponseDto;
import com.semoi.semo.campus.dto.response.CampusRecScoreResponseDto;
import com.semoi.semo.campus.dto.response.CampusResponseDto;
import com.semoi.semo.campus.dto.response.CourseResponseDto;

import java.util.List;

public interface CampusService {
    Campus getCampusOrElseThrow(String name);
    List<CampusRecScoreResponseDto> getCampusRecRankingByYear(int year);
    List<CampusActScoreResponseDto> getCampusActRankingByYear(int year);

    void calculateAndSaveCampusYearlyScores();

    List<CampusResponseDto> getCampusList();
    List<CourseResponseDto> getCourseListInCampus(Long campusId);
}
