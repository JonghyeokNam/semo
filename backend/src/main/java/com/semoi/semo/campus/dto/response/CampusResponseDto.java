package com.semoi.semo.campus.dto.response;

import com.semoi.semo.campus.domain.Campus;

public record CampusResponseDto(
        Long campusId,
        String name
) {
    public static CampusResponseDto of(Campus campus) {
        return new CampusResponseDto(
                campus.getCampusId(),
                campus.getName()
        );
    }
}
