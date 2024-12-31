package com.semoi.semo.campus.controller;

import com.semoi.semo.campus.dto.response.CampusActScoreResponseDto;
import com.semoi.semo.campus.dto.response.CampusRecScoreResponseDto;
import com.semoi.semo.campus.dto.response.CampusResponseDto;
import com.semoi.semo.campus.dto.response.CourseResponseDto;
import com.semoi.semo.campus.service.CampusService;
import com.semoi.semo.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/campuses")
@Slf4j
@RequiredArgsConstructor
public class CampusController {

    private final CampusService campusService;

    @Operation(
            summary = "연도별 참여 순위 조회",
            description = "특정 연도의 캠퍼스 참여 점수를 기반으로 순위를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청 성공 - 참여 순위 데이터 반환",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CampusActScoreResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류"
                    )
            }
    )
    @GetMapping("/act/{year}")
    public Response<List<CampusActScoreResponseDto>> getActRanking(@PathVariable int year) {
        return Response.success(campusService.getCampusActRankingByYear(year));
    }

    @Operation(
            summary = "연도별 모집 순위 조회",
            description = "특정 연도의 캠퍼스 모집 점수를 기반으로 순위를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청 성공 - 모집 순위 데이터 반환",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CampusRecScoreResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류"
                    )
            }
    )
    @GetMapping("/recruit/{year}")
    public Response<List<CampusRecScoreResponseDto>> getRecruitRanking(@PathVariable int year) {
        return Response.success(campusService.getCampusRecRankingByYear(year));
    }

    @GetMapping("/test/scheduler")
    public Response<Void> demonstrationScheduler() {
        campusService.calculateAndSaveCampusYearlyScores();
        return Response.success();
    }

    @GetMapping
    public Response<List<CampusResponseDto>> getCampusList() {
        return Response.success(campusService.getCampusList());
    }

    @GetMapping("/{campusId}/courses")
    public Response<List<CourseResponseDto>> getCourseListInCampus(@PathVariable Long campusId) {
        return Response.success(campusService.getCourseListInCampus(campusId));
    }

}
