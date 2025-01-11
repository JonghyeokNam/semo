package com.semoi.semo.campus.dto.response;

import com.semoi.semo.campus.domain.Course;

public record CourseResponseDto(
        Long courseId,
        String name,
        Long campusId
) {
    public static CourseResponseDto of(Course course) {
        return new CourseResponseDto(
                course.getCourseId(),
                course.getName(),
                course.getCampus().getCampusId()
        );
    }
}
