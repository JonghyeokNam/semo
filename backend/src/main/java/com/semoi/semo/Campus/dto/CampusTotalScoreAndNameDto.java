package com.semoi.semo.Campus.dto;

import com.semoi.semo.Campus.enums.CampusName;

public record CampusTotalScoreAndNameDto(
        Long actScore,
        Long recScore,
        Long campusId
) {
}
